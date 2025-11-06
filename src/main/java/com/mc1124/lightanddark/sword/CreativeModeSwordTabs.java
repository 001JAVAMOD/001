package com.mc1124.lightanddark.sword;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import com.mc1124.lightanddark.LightAndDarkMod;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeSwordTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LightAndDarkMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_SWORD_TAB = 
            CREATIVE_MODE_TABS.register("lightanddark_sword_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Divine_Sword.EXAMPLE_DIVINE_SWORD.get()))
                    .title(Component.translatable("itemGroup.lightanddark_sword_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Divine_Sword.EXAMPLE_DIVINE_SWORD.get());
                        Poutput.accept(Ruby_Sword.EXAMPLE_RUBY_SWORD.get());
                        Poutput.accept(Sapphire_Sword.EXAMPLE_SAPPHIRE_SWORD.get());
                        Poutput.accept(White_Spear.EXAMPLE_WHITE_SPEA.get());
                    })
                    .build()
    );
}
