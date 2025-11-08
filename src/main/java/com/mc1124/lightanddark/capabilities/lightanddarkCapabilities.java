package com.mc1124.lightanddark.capabilities;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class lightanddarkCapabilities {
    public static final Function<Item, CapabilityItem.Builder> divne_sword = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.GREATSWORD).styleProvider((playerpatch) ->
                CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.GREATSWORD)
                .swingSound((SoundEvent)EpicFightSounds.WHOOSH_BIG.get())
                .canBePlacedOffhand(false)
                .reach(1.0F)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        new AnimationManager.AnimationAccessor[]{
                                Animations.GREATSWORD_AUTO1,
                                Animations.GREATSWORD_AUTO2,
                                Animations.GREATSWORD_DASH,
                                Animations.GREATSWORD_AIR_SLASH
                })
                .innateSkill(CapabilityItem.Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.FLY, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.CREATIVE_FLY, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.CREATIVE_IDLE, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? (SoundEvent)EpicFightSounds.BLUNT_HIT.get() : (SoundEvent)EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? (HitParticleType)EpicFightParticles.HIT_BLUNT.get() : (HitParticleType)EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> ruby_sword = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.LONGSWORD).styleProvider((playerpatch) -> {
            if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                return CapabilityItem.Styles.ONE_HAND;
            } else if (playerpatch instanceof PlayerPatch) {
                PlayerPatch<?> tplayerpatch = (PlayerPatch)playerpatch;
                return tplayerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? CapabilityItem.Styles.OCHS : CapabilityItem.Styles.TWO_HAND;
            } else {
                return CapabilityItem.Styles.TWO_HAND;
            }
        }).collider(ColliderPreset.LONGSWORD).canBePlacedOffhand(false)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_AUTO1,
                                        Animations.LONGSWORD_AUTO2,
                                        Animations.LONGSWORD_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_AUTO1,
                                        Animations.LONGSWORD_AUTO2,
                                        Animations.LONGSWORD_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.OCHS
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .innateSkill(CapabilityItem.Styles.ONE_HAND,
                        (itemstack) -> EpicFightSkills.SHARP_STAB)
                .innateSkill(CapabilityItem.Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.LIECHTENAUER)
                .innateSkill(CapabilityItem.Styles.OCHS,
                        (itemstack) -> EpicFightSkills.LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON
                        , LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND,
                        LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? (SoundEvent)EpicFightSounds.BLUNT_HIT.get() : (SoundEvent)EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? (HitParticleType) EpicFightParticles.HIT_BLUNT.get() : (HitParticleType)EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> sapphire_sword = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.LONGSWORD).styleProvider((playerpatch) -> {
                    if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                        return CapabilityItem.Styles.ONE_HAND;
                    } else if (playerpatch instanceof PlayerPatch) {
                        PlayerPatch<?> tplayerpatch = (PlayerPatch)playerpatch;
                        return tplayerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? CapabilityItem.Styles.OCHS : CapabilityItem.Styles.TWO_HAND;
                    } else {
                        return CapabilityItem.Styles.TWO_HAND;
                    }
                }).collider(ColliderPreset.LONGSWORD).canBePlacedOffhand(false)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_AUTO1,
                                        Animations.LONGSWORD_AUTO2,
                                        Animations.LONGSWORD_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_AUTO1,
                                        Animations.LONGSWORD_AUTO2,
                                        Animations.LONGSWORD_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.OCHS
                        , new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                                        Animations.LONGSWORD_LIECHTENAUER_AUTO3,
                                        Animations.LONGSWORD_DASH,
                                        Animations.LONGSWORD_AIR_SLASH
                                })
                .innateSkill(CapabilityItem.Styles.ONE_HAND,
                        (itemstack) -> EpicFightSkills.SHARP_STAB)
                .innateSkill(CapabilityItem.Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.LIECHTENAUER)
                .innateSkill(CapabilityItem.Styles.OCHS,
                        (itemstack) -> EpicFightSkills.LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON
                        , LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND,
                        LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.OCHS,
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? (SoundEvent)EpicFightSounds.BLUNT_HIT.get() : (SoundEvent)EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? (HitParticleType) EpicFightParticles.HIT_BLUNT.get() : (HitParticleType)EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> white_spear = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SPEAR)
                .swingSound((SoundEvent)EpicFightSounds.WHOOSH_ROD.get()).styleProvider((playerpatch) ->
                        playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND)
                                .getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.SPEAR).canBePlacedOffhand(false).reach(1.0F).newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.SPEAR_ONEHAND_AUTO,
                                        Animations.SPEAR_DASH,
                                        Animations.SPEAR_ONEHAND_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.SPEAR_TWOHAND_AUTO1,
                                        Animations.SPEAR_TWOHAND_AUTO2,
                                        Animations.SPEAR_DASH,
                                        Animations.SPEAR_TWOHAND_AIR_SLASH
                                })
                .newStyleCombo(CapabilityItem.Styles.MOUNT,
                        new AnimationManager.AnimationAccessor[]
                                {
                                        Animations.SPEAR_MOUNT_ATTACK
                                })
                .innateSkill(CapabilityItem.Styles.ONE_HAND,
                        (itemstack) -> EpicFightSkills.HEARTPIERCER)
                .innateSkill(CapabilityItem.Styles.TWO_HAND,
                        (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND,
                        LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND,
                        LivingMotions.BLOCK, Animations.SPEAR_GUARD);
        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? (SoundEvent)EpicFightSounds.BLUNT_HIT.get() : (SoundEvent)EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? (HitParticleType)EpicFightParticles.HIT_BLUNT.get() : (HitParticleType)EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.parse("divine_sword"),divne_sword);
        event.getTypeEntry().put(ResourceLocation.parse("ruby_sword"),ruby_sword);
        event.getTypeEntry().put(ResourceLocation.parse("sapphire_sword"),sapphire_sword);
        event.getTypeEntry().put(ResourceLocation.parse("white_spear"),white_spear);
    }
}
