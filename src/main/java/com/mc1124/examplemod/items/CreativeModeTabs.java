package com.mc1124.examplemod.items;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = 
            CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.EXAMPLE_ITEM.get()))
                    .title(Component.translatable("itemGroup.example_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Items.EXAMPLE_ITEM.get());
                        Poutput.accept(Sword.EXAMPLE_SWORD_ITEM.get());
                    })
                    .build()
    );
}
