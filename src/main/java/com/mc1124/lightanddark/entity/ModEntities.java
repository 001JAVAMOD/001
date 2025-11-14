package com.mc1124.lightanddark.entity;

import com.mc1124.lightanddark.LightAndDarkMod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister
                        .create(ForgeRegistries.ENTITY_TYPES, LightAndDarkMod.MOD_ID);

        public static final RegistryObject<EntityType<DarkShadow>> DARK_SHADOW = ENTITIES.register("dark_shadow",
                        () -> EntityType.Builder.of(DarkShadow::new, MobCategory.MONSTER)
                                        .sized(0.6f, 1.95f)
                                        .clientTrackingRange(8)
                                        .build(LightAndDarkMod.MOD_ID + ":dark_shadow"));
}