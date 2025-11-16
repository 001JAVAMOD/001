package com.mc1124.lightanddark.client;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String CATEGORY = "key.category." + LightAndDarkMod.MOD_ID;
    
    public static final KeyMapping OPEN_GUI_KEY = new KeyMapping(
            "key." + LightAndDarkMod.MOD_ID + ".open_gui",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B, // 默认按键 B
            CATEGORY
    );
}