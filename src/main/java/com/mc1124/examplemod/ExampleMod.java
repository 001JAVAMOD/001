package com.mc1124.examplemod;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

import com.mc1124.examplemod.items.CreativeModeItemTabs;
import com.mc1124.examplemod.sword.CreativeModeSwordTabs;
import com.mc1124.examplemod.block.CreativeModeBlockTabs;
import com.mc1124.examplemod.items.Items;
import com.mc1124.examplemod.sword.Divine_Sword;
import com.mc1124.examplemod.sword.White_Spear;
import com.mc1124.examplemod.block.Ruby_Ore;
import com.mc1124.examplemod.block.Sapphire_Ore;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MOD_ID)
public class ExampleMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "examplemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public ExampleMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        Items.register(modEventBus);
        Divine_Sword.register(modEventBus);
        White_Spear.register(modEventBus);
        Ruby_Ore.register(modEventBus);
        Sapphire_Ore.register(modEventBus);
        CreativeModeItemTabs.CREATIVE_MODE_TABS.register(modEventBus);
        CreativeModeSwordTabs.CREATIVE_MODE_TABS.register(modEventBus);
        CreativeModeBlockTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
