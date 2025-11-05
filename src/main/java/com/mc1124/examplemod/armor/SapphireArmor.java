package com.mc1124.examplemod.armor;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SapphireArmor {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MOD_ID);

    // 蓝宝石头盔
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
        () -> new ArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, 
                           new Item.Properties()));

    // 蓝宝石胸甲
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
        () -> new ArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, 
                           new Item.Properties()));

    // 蓝宝石护腿
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
        () -> new ArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, 
                           new Item.Properties()));

    // 蓝宝石靴子
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
        () -> new ArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, 
                           new Item.Properties()));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
