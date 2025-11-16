package com.mc1124.lightanddark.network;

import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TeamSyncPacket {
    private final Map<String, String> teams;
    private final Map<String, String> teamPlayers;

    public TeamSyncPacket(Map<String, String> teams, Map<String, String> teamPlayers) {
        this.teams = teams;
        this.teamPlayers = teamPlayers;
    }

    public TeamSyncPacket(FriendlyByteBuf buf) {
        int teamCount = buf.readInt();
        this.teams = new HashMap<>();
        for (int i = 0; i < teamCount; i++) {
            String teamId = buf.readUtf();
            String teamName = buf.readUtf();
            teams.put(teamId, teamName);
        }

        int playerCount = buf.readInt();
        this.teamPlayers = new HashMap<>();
        for (int i = 0; i < playerCount; i++) {
            String teamId = buf.readUtf();
            String playerList = buf.readUtf();
            teamPlayers.put(teamId, playerList);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(teams.size());
        for (Map.Entry<String, String> entry : teams.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeUtf(entry.getValue());
        }

        buf.writeInt(teamPlayers.size());
        for (Map.Entry<String, String> entry : teamPlayers.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeUtf(entry.getValue());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 客户端接收队伍同步数据
            TeamManager.updateTeamsFromServer(teams, teamPlayers);
        });
        return true;
    }
}