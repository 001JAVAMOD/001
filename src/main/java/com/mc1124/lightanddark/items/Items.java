package com.mc1124.lightanddark.items;

import com.mc1124.lightanddark.LightAndDarkMod;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LightAndDarkMod.MOD_ID);

    public static final RegistryObject<Item> EXAMPLE_WHITEBORD = 
            ITEMS.register("lightanddark_whiteboard", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EXAMPLE_RUBY = 
            ITEMS.register("lightanddark_ruby", () -> new Item(new Item.Properties()));
    
    public static final RegistryObject<Item> EXAMPLE_SAPPHIRE = 
            ITEMS.register("lightanddark_sapphire", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EXAMPLE_WHITEBOARD_INGOT = 
            ITEMS.register("lightanddark_whiteboard_ingot", () -> new Item(new Item.Properties()));

    public static final String EXAMPLE_DIVINE_SWORD = null;
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
