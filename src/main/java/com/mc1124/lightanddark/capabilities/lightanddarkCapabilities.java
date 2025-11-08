package com.mc1124.lightanddark.capabilities;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class lightanddarkCapabilities {
    public static final Function<Item, CapabilityItem.Builder> divne_sword = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.AXE)
                .hitSound((SoundEvent) EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.TOOLS)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND
                        , new AnimationManager.AnimationAccessor[]{
                                Animations.GREATSWORD_AUTO1,
                                Animations.GREATSWORD_AUTO2,
                                Animations.GREATSWORD_DASH,
                                Animations.GREATSWORD_AIR_SLASH
                })
                .newStyleCombo(CapabilityItem.Styles.MOUNT, new AnimationManager.AnimationAccessor[]{
                        Animations.BIPED_HOLD_GREATSWORD
                })
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
        if (item instanceof TieredItem tieredItem) {
            int harvestLevel = tieredItem.getTier().getLevel();
            if (harvestLevel != 0) {
                builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute) EpicFightAttributes.ARMOR_NEGATION.get(), EpicFightAttributes.getArmorNegationModifier((double)10.0F * (double)harvestLevel)));
            }

            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.7 + 0.3 * (double)harvestLevel)));
        }

        return builder;
    };

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.parse("divine_sword"),divne_sword);
    }
}
