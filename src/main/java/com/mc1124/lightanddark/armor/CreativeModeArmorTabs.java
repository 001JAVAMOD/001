package com.mc1124.lightanddark.armor;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import com.mc1124.lightanddark.lightanddarkMod;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeArmorTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, lightanddarkMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_ARMOR_TAB = 
            CREATIVE_MODE_TABS.register("lightanddark_armor_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(RubyArmor.RUBY_HELMET.get()))
                    .title(Component.translatable("itemGroup.lightanddark_armor_tab"))
                    .displayItems((pOarameters, Poutput) -> {
                        Poutput.accept(RubyArmor.RUBY_HELMET.get());
                        Poutput.accept(RubyArmor.RUBY_CHESTPLATE.get());
                        Poutput.accept(RubyArmor.RUBY_LEGGINGS.get());
                        Poutput.accept(RubyArmor.RUBY_BOOTS.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_HELMET.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_CHESTPLATE.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_LEGGINGS.get());
                        Poutput.accept(SapphireArmor.SAPPHIRE_BOOTS.get());
                    })
                    .build()
    );
}
