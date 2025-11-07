package com.mc1124.lightanddark.procedures;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;

import javax.annotation.Nullable;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   modid = "lightanddarkmod",
   bus = Bus.MOD
)

public class Divine_SwordAnimationsProcedure {
    public Divine_SwordAnimationsProcedure() {
   }

   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ResourceLocation.tryParse("lightanddarkmod, player_animation"), 1000, Divine_SwordAnimationsProcedure::registerPlayerAnimations);
   }

   private static IAnimation registerPlayerAnimations(AbstractClientPlayer player) {
      return new ModifierLayer();
   }

   public static void execute() {
      execute((Event)null);
   }

   private static void execute(@Nullable Event event) {
   }
}
