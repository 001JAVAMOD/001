package com.mc1124.lightanddark.sword;

import com.mc1124.lightanddark.LightAndDarkMod;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;

public class Divine_Sword {
    public static final DeferredRegister<Item> DIVINE_SWORD =
            DeferredRegister.create(ForgeRegistries.ITEMS, LightAndDarkMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_DIVINE_SWORD = 
        DIVINE_SWORD.register("lightanddark_divine_sword", () -> new DivineSwordItem(
            Tiers.DIAMOND, // 材质等级
            30,          // 攻击伤害
            -2.4F,      // 攻击速度
            new Item.Properties()
        ));

        public static void register(IEventBus eventBus) {
        DIVINE_SWORD.register(eventBus);
    }
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, "Walk/Run/Idle", state -> {
        if (state.isMoving())
            return state.setAndContinue(MyEntity.this.isSprinting() ? DefaultAnimations.RUN : DefaultAnimations.WALK);

        return state.setAndContinue(DefaultAnimations.IDLE);
    }));
    }
}
