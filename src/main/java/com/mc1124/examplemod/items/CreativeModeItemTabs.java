package com.mc1124.examplemod.items;

import com.mc1124.examplemod.ExampleMod;
import com.mc1124.examplemod.block.Ruby_Ore;
import com.mc1124.examplemod.block.Sapphire_Ore;
import com.mc1124.examplemod.sword.Divine_Sword;
import com.mc1124.examplemod.sword.White_Spear;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeItemTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_ITEM_TAB = 
            CREATIVE_MODE_TABS.register("example_item_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.EXAMPLE_WHITEBORD.get()))
                    .title(Component.translatable("itemGroup.example_item_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Items.EXAMPLE_WHITEBORD.get());
                        Poutput.accept(Items.EXAMPLE_RUBY.get());
                        Poutput.accept(Items.EXAMPLE_SAPPHIRE.get());
                    })
                    .build()
    );
}
