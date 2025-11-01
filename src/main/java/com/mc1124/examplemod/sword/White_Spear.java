package com.mc1124.examplemod.sword;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class White_Spear {
    public static final DeferredRegister<Item> WHITE_SPEAR =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_WHITE_SPEA = 
        WHITE_SPEAR.register("example_white_spear", () -> new SwordItem(
            Tiers.DIAMOND, // 材质等级
            10,          // 攻击伤害
            -3.0F,      // 攻击速度
            new Item.Properties()
        ));
        public static void register(IEventBus eventBus) {
        WHITE_SPEAR.register(eventBus);
    }
}
