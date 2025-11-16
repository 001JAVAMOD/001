package com.mc1124.lightanddark.client;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.client.gui.TeamHUD;
import com.mc1124.lightanddark.client.gui.TeamSelectionScreen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LightAndDarkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.OPEN_GUI_KEY);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        // 注册队伍HUD
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "team_hud", (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
            TeamHUD.getInstance().render(guiGraphics, partialTicks);
        });
    }

    @Mod.EventBusSubscriber(modid = LightAndDarkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();
            
            if (minecraft.player == null) return;
            
            if (KeyBindings.OPEN_GUI_KEY.consumeClick()) {
                // 打开队伍选择GUI
                minecraft.setScreen(new TeamSelectionScreen());
            }
        }
    }
}