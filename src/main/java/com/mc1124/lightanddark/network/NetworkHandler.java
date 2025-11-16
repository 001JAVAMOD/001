package com.mc1124.lightanddark.network;

import com.mc1124.lightanddark.LightAndDarkMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(LightAndDarkMod.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int id = 0;
        INSTANCE.registerMessage(id++, TeamSyncPacket.class, TeamSyncPacket::toBytes, TeamSyncPacket::new, TeamSyncPacket::handle);
        INSTANCE.registerMessage(id++, PlayerTeamPacket.class, PlayerTeamPacket::toBytes, PlayerTeamPacket::new, PlayerTeamPacket::handle);
        INSTANCE.registerMessage(id++, CreateTeamPacket.class, CreateTeamPacket::toBytes, CreateTeamPacket::new, CreateTeamPacket::handle);
        INSTANCE.registerMessage(id++, DeleteTeamPacket.class, DeleteTeamPacket::toBytes, DeleteTeamPacket::new, DeleteTeamPacket::handle);
    }
}