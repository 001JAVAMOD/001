package com.mc1124.lightanddark.sword;

import com.mc1124.lightanddark.LightAndDarkMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Ruby_Sword {
    public static final DeferredRegister<Item> RUBY_SWORD =
            DeferredRegister.create(ForgeRegistries.ITEMS, LightAndDarkMod.MOD_ID);

    public static final RegistryObject<Item> LIGHTANDDARK_RUBY_SWORD = 
        RUBY_SWORD.register("lightanddark_ruby_sword", () -> new SwordItem(
            Tiers.DIAMOND, // 材质等级
            16,          // 攻击伤害
            -2.4F,      // 攻击速度
            new Item.Properties()
        ));
        public static void register(IEventBus eventBus) {
        RUBY_SWORD.register(eventBus);
    }
}
