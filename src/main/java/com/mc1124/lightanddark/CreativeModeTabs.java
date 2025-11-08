package com.mc1124.lightanddark;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import com.mc1124.lightanddark.armor.*;
import com.mc1124.lightanddark.items.*;
import com.mc1124.lightanddark.sword.*;
import com.mc1124.lightanddark.block.*;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LightAndDarkMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = 
            CREATIVE_MODE_TABS.register("lightanddark_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Divine_Sword.EXAMPLE_DIVINE_SWORD.get()))
                    .title(Component.translatable("itemGroup.lightanddark_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(Ruby_Ore.EXAMPLE_RUBY_ORE.get());
                        Poutput.accept(Sapphire_Ore.EXAMPLE_SAPPHIRE_ORE.get());
                        Poutput.accept(Ruby_Ore.EXAMPLE_DEEPSLATE_RUBY_ORE.get());
                        Poutput.accept(Sapphire_Ore.EXAMPLE_DEEPSLATE_SAPPHIRE_ORE.get());
                        Poutput.accept(Whiteboard_Ore.EXAMPLE_WHITEBOARD_ORE.get());
                        Poutput.accept(Whiteboard_Ore.EXAMPLE_DEEPSLATE_WHITEBOARD_ORE.get());
                        Poutput.accept(Items.EXAMPLE_WHITEBORD.get());
                        Poutput.accept(Items.EXAMPLE_RUBY.get());
                        Poutput.accept(Items.EXAMPLE_SAPPHIRE.get());
                        Poutput.accept(Items.EXAMPLE_WHITEBOARD_INGOT.get());
                        Poutput.accept(RubyArmor.RUBY_HELMET.get());
                        Poutput.accept(RubyArmor.RUBY_CHESTPLATE.get());
                        Poutput.accept(RubyArmor.RUBY_LEGGINGS.get());
                        Poutput.accept(RubyArmor.RUBY_BOOTS.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_HELMET.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_CHESTPLATE.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_LEGGINGS.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_BOOTS.get());
                        Poutput.accept(Divine_Sword.EXAMPLE_DIVINE_SWORD.get());
                        Poutput.accept(Ruby_Sword.EXAMPLE_RUBY_SWORD.get());
                        Poutput.accept(Sapphire_Sword.EXAMPLE_SAPPHIRE_SWORD.get());
                        Poutput.accept(White_Spear.EXAMPLE_WHITE_SPEA.get());
                    })
                    .build()
    );
}
