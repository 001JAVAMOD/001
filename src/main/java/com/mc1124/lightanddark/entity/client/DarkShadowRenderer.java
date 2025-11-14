package com.mc1124.lightanddark.entity.client;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.entity.DarkShadow;
import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DarkShadowRenderer extends GeoEntityRenderer<DarkShadow> {
    public DarkShadowRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DarkShadowModel());
    }

    public static class DarkShadowModel extends GeoModel<DarkShadow> {
        @Override
        public ResourceLocation getModelResource(DarkShadow object) {
            return ResourceLocation.fromNamespaceAndPath(LightAndDarkMod.MOD_ID, "geo/dark_shadow.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(DarkShadow object) {
            return ResourceLocation.fromNamespaceAndPath(LightAndDarkMod.MOD_ID, "textures/entity/dark_shadow.png");
        }

        @Override
        public ResourceLocation getAnimationResource(DarkShadow object) {
            return ResourceLocation.fromNamespaceAndPath(LightAndDarkMod.MOD_ID,
                    "animations/dark_shadow.animation.json");
        }
    }
}