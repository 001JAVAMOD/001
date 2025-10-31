package com.mc1124.examplemod.items;

import com.mc1124.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Sword {
    public static final DeferredRegister<Item> SWORD =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_SWORD_ITEM = 
        SWORD.register("example_sword_item", () -> new SwordItem(
            Tiers.DIAMOND, // 材质等级
            96,          // 攻击伤害
            -2.4F,      // 攻击速度
            new Item.Properties()
        ));
        public static void register(IEventBus eventBus) {
        SWORD.register(eventBus);
    }
}
