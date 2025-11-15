package com.mc1124.lightanddark.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DarkShadow extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    // 添加攻击状态跟踪
    private boolean isAttacking = false;
    private int attackCooldown = 0;

    public DarkShadow(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // GeckoLib 动画相关
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // 主控制器 - 处理移动和攻击动画
        controllers.add(new AnimationController<>(this, "main_controller", 5, this::mainPredicate));
        
        // 攻击控制器 - 专门处理攻击动画
        controllers.add(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    private <T extends GeoAnimatable> PlayState mainPredicate(AnimationState<T> event) {
        if (this.isAttacking) {
            // 攻击时播放攻击动画
            return PlayState.STOP; // 让攻击控制器处理攻击动画
        } else if (event.isMoving()) {
            // 移动时播放跑步动画
            if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 8.0F) {
                // 靠近目标时播放冲刺动画
                event.getController().setAnimation(RawAnimation.begin()
                        .then("animation.dark_shadow.run", Animation.LoopType.LOOP));
            } else {
                // 普通移动时播放行走动画
                event.getController().setAnimation(RawAnimation.begin()
                        .then("animation.dark_shadow.walk", Animation.LoopType.LOOP));
            }
        } else {
            // 静止时播放闲置动画
            event.getController().setAnimation(RawAnimation.begin()
                    .then("animation.dark_shadow.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState attackPredicate(AnimationState<T> event) {
        if (this.isAttacking) {
            event.getController().setAnimation(RawAnimation.begin()
                    .then("animation.dark_shadow.attack", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        
        // 更新攻击状态和冷却
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
            if (this.attackCooldown <= 0) {
                this.isAttacking = false;
            }
        }
    }

    // 重写攻击方法以触发攻击动画
    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        this.isAttacking = true;
        this.attackCooldown = 20; // 设置攻击冷却时间（1秒，20 ticks）
        
        boolean result = super.doHurtTarget(target);
        
        // 触发攻击动画
        this.triggerAnim("attack_controller", "attack");
        
        return result;
    }

    // 添加自定义方法用于动画触发
    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
    }

    public boolean isAttacking() {
        return this.isAttacking;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}