package com.mc1124.examplemod.block;

import com.mc1124.examplemod.ExampleMod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeBlockTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_BLOCK_TAB = 
            CREATIVE_MODE_TABS.register("example_block_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Ruby_Ore.EXAMPLE_RUBY_ORE.get()))
                    .title(Component.translatable("itemGroup.example_block_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Ruby_Ore.EXAMPLE_RUBY_ORE.get());
                        Poutput.accept(Ruby_Ore.EXAMPLE_DEEPSLATE_RUBY_ORE.get());
                        Poutput.accept(Sapphire_Ore.EXAMPLE_SAPPHIRE_ORE.get());
                        Poutput.accept(Sapphire_Ore.EXAMPLE_DEEPSLATE_SAPPHIRE_ORE.get());
                    })
                    .build()
    );
}
