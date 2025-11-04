package com.mc1124.examplemod.block;

import com.mc1124.examplemod.ExampleMod;
import com.mc1124.examplemod.items.Items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Ruby_Ore {
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MOD_ID);

    public static final RegistryObject<Block> EXAMPLE_RUBY_ORE = 
        registerBlock("example_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
            .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EXAMPLE_DEEPSLATE_RUBY_ORE = 
        registerBlock("example_deepslate_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
            .requiresCorrectToolForDrops()));

    private static <T extends Block> void registerBlockItems(String name, RegistryObject<T> block) {
        Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
