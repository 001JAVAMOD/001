package com.mc1124.examplemod.sword;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeSwordTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_SWORD_TAB = 
            CREATIVE_MODE_TABS.register("example_sword_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Divine_Sword.EXAMPLE_DIVINE_SWORD.get()))
                    .title(Component.translatable("itemGroup.example_sword_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Divine_Sword.EXAMPLE_DIVINE_SWORD.get());
                        Poutput.accept(White_Spear.EXAMPLE_WHITE_SPEA.get());
                    })
                    .build()
    );
}
