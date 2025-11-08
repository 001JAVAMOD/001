package com.mc1124.lightanddark.sword;


import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DivineSwordItem extends SwordItem {

    public DivineSwordItem(Tiers tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isCrouching()) {
            if (!world.isClientSide) {
                // 示例：右键时给予玩家速度与生命恢复效果
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3000, 4));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3000, 4));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 4));
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 3000, 19));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3000, 5));

            // 设置冷却时间（可选）
                player.getCooldowns().addCooldown(this, 4000); // 20秒冷却
            }
            return InteractionResultHolder.success(stack);
        }
    return InteractionResultHolder.pass(stack);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        tooltip.add(Component.translatable("tooltip.lightanddark_divine_sword.ability"));
        tooltip.add(Component.translatable("tooltip.lightanddark_divine_sword.ability1"));
    }
}