package com.mc1124.lightanddark.armor;

import com.mc1124.lightanddark.lightanddarkMod;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RubyArmor {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, lightanddarkMod.MOD_ID);

    // 红宝石头盔
    public static final RegistryObject<Item> RUBY_HELMET = ITEMS.register("ruby_helmet",
        () -> new ArmorItem(RubyArmorMaterials.RUBY, ArmorItem.Type.HELMET, 
                           new Item.Properties()));

    // 红宝石胸甲
    public static final RegistryObject<Item> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate",
        () -> new ArmorItem(RubyArmorMaterials.RUBY, ArmorItem.Type.CHESTPLATE, 
                           new Item.Properties()));

    // 红宝石护腿
    public static final RegistryObject<Item> RUBY_LEGGINGS = ITEMS.register("ruby_leggings",
        () -> new ArmorItem(RubyArmorMaterials.RUBY, ArmorItem.Type.LEGGINGS, 
                           new Item.Properties()));

    // 红宝石靴子
    public static final RegistryObject<Item> RUBY_BOOTS = ITEMS.register("ruby_boots",
        () -> new ArmorItem(RubyArmorMaterials.RUBY, ArmorItem.Type.BOOTS, 
                           new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
