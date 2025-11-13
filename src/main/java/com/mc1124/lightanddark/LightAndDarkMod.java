package com.mc1124.lightanddark;

import com.mc1124.lightanddark.armor.RubyArmor;
import com.mc1124.lightanddark.armor.SapphireArmor;
import com.mc1124.lightanddark.block.Ruby_Ore;
import com.mc1124.lightanddark.block.Sapphire_Ore;
import com.mc1124.lightanddark.block.Whiteboard_Ore;
import com.mc1124.lightanddark.items.Items;
import com.mc1124.lightanddark.sword.Divine_Sword;
import com.mc1124.lightanddark.sword.Ruby_Sword;
import com.mc1124.lightanddark.sword.Sapphire_Sword;
import com.mc1124.lightanddark.sword.White_Spear;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LightAndDarkMod.MOD_ID)
public class LightAndDarkMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "lightanddarkmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under
    // the "lightanddarkmod" namespace

    public LightAndDarkMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        Items.register(modEventBus);
        Divine_Sword.register(modEventBus);
        Ruby_Sword.register(modEventBus);
        Sapphire_Sword.register(modEventBus);
        White_Spear.register(modEventBus);
        Ruby_Ore.register(modEventBus);
        Sapphire_Ore.register(modEventBus);
        Whiteboard_Ore.register(modEventBus);
        RubyArmor.register(modEventBus);
        SapphireArmor.register(modEventBus);
        CreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the
        // config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the lightanddark block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods
    // in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
