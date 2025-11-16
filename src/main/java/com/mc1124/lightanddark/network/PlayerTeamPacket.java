package com.mc1124.lightanddark.network;

import com.mc1124.lightanddark.system.TeamManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerTeamPacket {
    private final UUID playerUUID;
    private final String teamId;
    private final String playerName;

    public PlayerTeamPacket(UUID playerUUID, String teamId, String playerName) {
        this.playerUUID = playerUUID;
        this.teamId = teamId;
        this.playerName = playerName;
    }

    public PlayerTeamPacket(FriendlyByteBuf buf) {
        this.playerUUID = buf.readUUID();
        this.teamId = buf.readUtf();
        this.playerName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(playerUUID);
        buf.writeUtf(teamId);
        buf.writeUtf(playerName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 客户端接收玩家队伍更新
            TeamManager.updatePlayerTeam(playerUUID, teamId, playerName);
        });
        return true;
    }
}