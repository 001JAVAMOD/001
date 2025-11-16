package com.mc1124.lightanddark.server;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.network.NetworkHandler;
import com.mc1124.lightanddark.network.TeamSyncPacket;
import com.mc1124.lightanddark.system.TeamDataManager;
import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LightAndDarkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        System.out.println("服务器启动，初始化队伍系统");
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // 延迟同步，确保网络已经初始化
            serverPlayer.server.execute(() -> {
                syncTeamsToClient(serverPlayer);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.server.execute(() -> {
                syncTeamsToClient(serverPlayer);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.server.execute(() -> {
                syncTeamsToClient(serverPlayer);
            });
        }
    }

    /**
     * 同步队伍数据到客户端
     */
    public static void syncTeamsToClient(ServerPlayer player) {
        // 检查网络是否已初始化
        if (NetworkHandler.INSTANCE == null) {
            System.err.println("网络处理器未初始化，跳过队伍同步");
            return;
        }

        // 准备队伍数据
        Map<String, String> teams = new HashMap<>();
        // 这里需要从持久化存储加载队伍数据
        // 暂时使用空数据
        
        // 准备玩家队伍数据
        Map<String, String> teamPlayersData = new HashMap<>();
    for (String teamId : teams.keySet()) {
        StringBuilder playerList = new StringBuilder();
        for (UUID playerUUID : TeamManager.getTeamPlayerUUIDs(teamId)) {
            if (playerList.length() > 0) {
                playerList.append(",");
            }
            playerList.append(playerUUID.toString());
        }
        teamPlayersData.put(teamId, playerList.toString());
    }
        
        // 发送同步包
        TeamSyncPacket packet = new TeamSyncPacket(teams, teamPlayersData);
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    
        System.out.println("同步队伍数据给玩家: " + player.getGameProfile().getName() + ", 队伍数量: " + teams.size());
    }

    /**
     * 广播队伍更新给所有玩家
     */
    public static void broadcastTeamUpdate() {
    if (NetworkHandler.INSTANCE == null) {
        System.err.println("网络处理器未初始化，跳过队伍广播");
        return;
    }

    // 获取真实的队伍数据
    Map<String, String> teams = TeamManager.getCustomTeams();
    
    // 准备玩家队伍数据
    Map<String, String> teamPlayersData = new HashMap<>();
    for (String teamId : teams.keySet()) {
        StringBuilder playerList = new StringBuilder();
        for (UUID playerUUID : TeamManager.getTeamPlayerUUIDs(teamId)) {
            if (playerList.length() > 0) {
                playerList.append(",");
            }
            playerList.append(playerUUID.toString());
        }
        teamPlayersData.put(teamId, playerList.toString());
    }
    
    // 广播给所有玩家
    TeamSyncPacket packet = new TeamSyncPacket(teams, teamPlayersData);
    NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    
    System.out.println("广播队伍更新，队伍数量: " + teams.size());
    }

    @SubscribeEvent
    public static void onServerStopping(net.minecraftforge.event.server.ServerStoppingEvent event) {
        // 服务器关闭时保存数据
        TeamDataManager.saveTeams(event.getServer());
    }

    @SubscribeEvent  
    public static void onServerStarted(net.minecraftforge.event.server.ServerStartedEvent event) {
        // 服务器启动完成后加载数据
        TeamDataManager.loadTeams(event.getServer());
    }
}