package com.mc1124.examplemod;

import com.mc1124.examplemod.block.Ruby_Ore;
import com.mc1124.examplemod.block.Sapphire_Ore;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

public class OreGeneration {
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_RUBY_ORE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, 
            new ResourceLocation(ExampleMod.MOD_ID, "ruby_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SAPPHIRE_ORE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, 
            new ResourceLocation(ExampleMod.MOD_ID, "sapphire_ore"));
    
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        
        List<OreConfiguration.TargetBlockState> rubyOreTargetList = List.of(
            OreConfiguration.target(stoneReplaceable, Ruby_Ore.EXAMPLE_RUBY_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceable, Ruby_Ore.EXAMPLE_RUBY_ORE.get().defaultBlockState())
        );
        
        List<OreConfiguration.TargetBlockState> sapphireOreTargetList = List.of(
            OreConfiguration.target(stoneReplaceable, Sapphire_Ore.EXAMPLE_SAPPHIRE_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceable, Sapphire_Ore.EXAMPLE_SAPPHIRE_ORE.get().defaultBlockState())
        );
        
        // Register configured features
        context.register(OVERWORLD_RUBY_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, 
            new OreConfiguration(rubyOreTargetList, 9))); // 9 = vein size
        
        context.register(OVERWORLD_SAPPHIRE_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, 
            new OreConfiguration(sapphireOreTargetList, 8))); // 8 = vein size
    }
    
    public static void registerOres() {
        // This would typically be called during mod initialization
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Add any event listeners for ore generation here
    }
}