package com.mc1124.lightanddark.armor;

import com.mc1124.lightanddark.LightAndDarkMod;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SapphireArmor {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, LightAndDarkMod.MOD_ID);

    // 蓝宝石头盔
    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
        () -> new CustomArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, 
                           new Item.Properties(),
                           "tooltip.lightanddark.helmet"));

    // 蓝宝石胸甲
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
        () -> new CustomArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, 
                           new Item.Properties(),
                           "tooltip.lightanddark.chestplate"));

    // 蓝宝石护腿
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
        () -> new CustomArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, 
                           new Item.Properties(),
                           "tooltip.lightanddark.leggings"));

    // 蓝宝石靴子
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
        () -> new CustomArmorItem(SapphireArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, 
                           new Item.Properties(),
                           "tooltip.lightanddark.boots"));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
