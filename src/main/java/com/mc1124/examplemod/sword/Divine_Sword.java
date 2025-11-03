package com.mc1124.examplemod.sword;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Divine_Sword {
    public static final DeferredRegister<Item> DIVINE_SWORD =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_DIVINE_SWORD = 
        DIVINE_SWORD.register("example_divine_sword", () -> new DivineSwordItem(
            Tiers.DIAMOND, // 材质等级
            30,          // 攻击伤害
            -2.4F,      // 攻击速度
            new Item.Properties()
        ));
        public static void register(IEventBus eventBus) {
        DIVINE_SWORD.register(eventBus);
    }
}
