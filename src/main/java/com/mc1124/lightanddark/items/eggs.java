package com.mc1124.lightanddark.items;

import com.mc1124.lightanddark.entity.ModEntities;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class eggs {
    public static final DeferredRegister<Item> SPAWN_EGGS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, "lightanddarkmod");

    public static final RegistryObject<Item> DARK_SHADOW_SPAWN_EGG = SPAWN_EGGS.register("dark_shadow_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DARK_SHADOW, 0x2F2F2F, 0x8B0000, 
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        SPAWN_EGGS.register(eventBus);
    }
}