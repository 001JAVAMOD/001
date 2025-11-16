package com.mc1124.lightanddark.system;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import com.mc1124.lightanddark.server.ServerEventHandler;

@Mod.EventBusSubscriber
public class TeamManager {
    private static final Map<UUID, String> playerTeams = new HashMap<>();
    private static final Map<String, String> customTeams = new HashMap<>();
    private static final Map<String, Set<UUID>> teamPlayers = new HashMap<>();
    private static final Map<UUID, String> playerNames = new HashMap<>();
    private static final Map<String, List<TeamMessage>> teamMessages = new HashMap<>(); // 存储队伍消息
    private static final int MAX_TEAMS = 2; // 最大队伍数量
    
    // 队伍消息类
    public static class TeamMessage {
        public final String playerName;
        public final String message;
        public final long timestamp;
        
        public TeamMessage(String playerName, String message) {
            this.playerName = playerName;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    // 创建新队伍
    public static boolean createTeam(String teamId, String teamName) {
    // 检查队伍数量限制
        if (customTeams.size() >= MAX_TEAMS) {
            return false;
        }
        
        if (customTeams.containsKey(teamId)) {
            return false;
        }
        
        // 检查队伍名称是否重复
        for (String existingName : customTeams.values()) {
            if (existingName.equals(teamName)) {
                return false;
            }
        }
        
        customTeams.put(teamId, teamName);
        teamPlayers.put(teamId, new HashSet<>());
        teamMessages.put(teamId, new ArrayList<>());
        return true;
    }
    
    // 添加获取最大队伍数量的方法
    public static int getMaxTeams() {
        return MAX_TEAMS;
    }
    
    // 添加获取当前队伍数量的方法
    public static int getCurrentTeamCount() {
        return customTeams.size();
    }

    // 获取队伍名称
    public static String getTeamName(String teamId) {
        return customTeams.getOrDefault(teamId, "未知队伍");
    }
    
    // 获取所有自定义队伍
    public static Map<String, String> getCustomTeams() {
        return new HashMap<>(customTeams);
    }
    
    // 删除队伍
    public static boolean deleteTeam(String teamId) {
    if (!customTeams.containsKey(teamId)) {
        return false;
    }
    
    // 移除队伍的所有玩家
    if (teamPlayers.containsKey(teamId)) {
        // 将队伍中的玩家设置为无队伍
        for (UUID playerUUID : teamPlayers.get(teamId)) {
            playerTeams.put(playerUUID, "none");
        }
        teamPlayers.get(teamId).clear();
        teamPlayers.remove(teamId);
    }
    
    // 移除队伍消息
    if (teamMessages.containsKey(teamId)) {
        teamMessages.get(teamId).clear();
        teamMessages.remove(teamId);
    }
    
    // 移除队伍
    customTeams.remove(teamId);
    
    return true;
    }
    
    // 设置玩家队伍
    public static void setPlayerTeam(Player player, String team) {
        UUID playerId = player.getUUID();
        String oldTeam = getPlayerTeam(player);
        
        // 从旧队伍移除
        if (!oldTeam.equals("none") && teamPlayers.containsKey(oldTeam)) {
            teamPlayers.get(oldTeam).remove(playerId);
        }
        
        // 添加到新队伍
        playerTeams.put(playerId, team);
        playerNames.put(playerId, player.getGameProfile().getName());
        
        if (!team.equals("none")) {
            if (!teamPlayers.containsKey(team)) {
                teamPlayers.put(team, new HashSet<>());
            }
            teamPlayers.get(team).add(playerId);
            
            // 确保消息列表存在
            if (!teamMessages.containsKey(team)) {
                teamMessages.put(team, new ArrayList<>());
            }
        }
        
        // 保存到玩家NBT数据
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag persistentData = serverPlayer.getPersistentData();
            CompoundTag modData = persistentData.getCompound("lightanddarkmod");
            modData.putString("team", team);
            modData.putString("playerName", player.getGameProfile().getName());
            persistentData.put("lightanddarkmod", modData);
        }
    }
    
    public static String getPlayerTeam(Player player) {
        return playerTeams.getOrDefault(player.getUUID(), "none");
    }
    
    // 检查玩家是否在队伍中
    public static boolean isPlayerInTeam(Player player, String teamId) {
        return teamPlayers.getOrDefault(teamId, new HashSet<>()).contains(player.getUUID());
    }
    
    // 获取队伍的所有玩家UUID
    public static Set<UUID> getTeamPlayerUUIDs(String teamId) {
        return teamPlayers.getOrDefault(teamId, new HashSet<>());
    }
    
    // 获取队伍的所有玩家名称
    public static List<String> getTeamPlayerNames(String teamId) {
        Set<UUID> playerUUIDs = getTeamPlayerUUIDs(teamId);
        List<String> playerNamesList = new ArrayList<>();
        
        for (UUID uuid : playerUUIDs) {
            String playerName = playerNames.get(uuid);
            if (playerName != null) {
                playerNamesList.add(playerName);
            }
        }
        
        return playerNamesList;
    }
    
    // 获取队伍的所有玩家信息（UUID和名称）
    public static Map<UUID, String> getTeamPlayers(String teamId) {
        Map<UUID, String> players = new HashMap<>();
        Set<UUID> playerUUIDs = getTeamPlayerUUIDs(teamId);
        
        for (UUID uuid : playerUUIDs) {
            String playerName = playerNames.get(uuid);
            if (playerName != null) {
                players.put(uuid, playerName);
            }
        }
        
        return players;
    }
    
    // 根据UUID获取玩家名称
    public static String getPlayerName(UUID playerUUID) {
        return playerNames.getOrDefault(playerUUID, "未知玩家");
    }
    
    // 获取队伍玩家数量
    public static int getTeamPlayerCount(String teamId) {
        return teamPlayers.getOrDefault(teamId, new HashSet<>()).size();
    }
    
    // 添加队伍消息
    public static void addTeamMessage(String teamId, String playerName, String message) {
        if (!teamMessages.containsKey(teamId)) {
            teamMessages.put(teamId, new ArrayList<>());
        }
        
        List<TeamMessage> messages = teamMessages.get(teamId);
        messages.add(new TeamMessage(playerName, message));
        
        // 限制消息数量，最多保存50条
        if (messages.size() > 50) {
            messages.remove(0);
        }
    }
    
    // 获取队伍消息
    public static List<TeamMessage> getTeamMessages(String teamId) {
        return teamMessages.getOrDefault(teamId, new ArrayList<>());
    }
    
    // 获取最近的消息（限制数量）
    public static List<TeamMessage> getRecentTeamMessages(String teamId, int maxCount) {
        List<TeamMessage> allMessages = getTeamMessages(teamId);
        if (allMessages.size() <= maxCount) {
            return allMessages;
        }
        return allMessages.subList(allMessages.size() - maxCount, allMessages.size());
    }
    
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        UUID playerUUID = player.getUUID();
        String playerName = player.getGameProfile().getName();
        
        playerNames.put(playerUUID, playerName);
        
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag modData = persistentData.getCompound("lightanddarkmod");
        
        if (modData.contains("team")) {
            String team = modData.getString("team");
            playerTeams.put(playerUUID, team);
            
            if (!team.equals("none") && teamPlayers.containsKey(team)) {
                teamPlayers.get(team).add(playerUUID);
            }
        }
    }

    public static void updatePlayerTeam(UUID playerUUID, String teamId, String playerName) {
    playerTeams.put(playerUUID, teamId);
    playerNames.put(playerUUID, playerName);
    
    if (!teamId.equals("none")) {
        if (!teamPlayers.containsKey(teamId)) {
            teamPlayers.put(teamId, new HashSet<>());
        }
        teamPlayers.get(teamId).add(playerUUID);
    }
}

public static void updateTeamsFromServer(Map<String, String> teams, Map<String, String> teamPlayersData) {
    // 清空现有数据
    customTeams.clear();
    teamPlayers.clear();
    
    // 更新队伍数据
    customTeams.putAll(teams);
    
    // 更新玩家队伍数据
    for (Map.Entry<String, String> entry : teamPlayersData.entrySet()) {
        String teamId = entry.getKey();
        String playerListStr = entry.getValue();
        
        if (!teamPlayers.containsKey(teamId)) {
            teamPlayers.put(teamId, new HashSet<>());
        }
        
        // 解析玩家UUID列表
        if (!playerListStr.isEmpty()) {
            String[] playerUUIDs = playerListStr.split(",");
            for (String uuidStr : playerUUIDs) {
                try {
                    UUID playerUUID = UUID.fromString(uuidStr);
                    teamPlayers.get(teamId).add(playerUUID);
                } catch (IllegalArgumentException e) {
                    System.err.println("无效的UUID: " + uuidStr);
                }
            }
        }
    }
}
    public static boolean createTeamOnServer(String teamId, String teamName) {
    if (customTeams.containsKey(teamId)) {
        return false;
    }
    customTeams.put(teamId, teamName);
    teamPlayers.put(teamId, new HashSet<>());
    teamMessages.put(teamId, new ArrayList<>());
    
    // 广播更新给所有客户端
    ServerEventHandler.broadcastTeamUpdate();
    return true;
    }
    public static void setPlayerTeamOnServer(Player player, String team) {
    UUID playerId = player.getUUID();
    String oldTeam = getPlayerTeam(player);
    
    // 从旧队伍移除
    if (!oldTeam.equals("none") && teamPlayers.containsKey(oldTeam)) {
        teamPlayers.get(oldTeam).remove(playerId);
    }
    
    // 添加到新队伍
    playerTeams.put(playerId, team);
    playerNames.put(playerId, player.getGameProfile().getName());
    
    if (!team.equals("none")) {
        if (!teamPlayers.containsKey(team)) {
            teamPlayers.put(team, new HashSet<>());
        }
        teamPlayers.get(team).add(playerId);
        
        if (!teamMessages.containsKey(team)) {
            teamMessages.put(team, new ArrayList<>());
        }
    }
    
    // 保存到玩家NBT数据
    if (player instanceof ServerPlayer serverPlayer) {
        CompoundTag persistentData = serverPlayer.getPersistentData();
        CompoundTag modData = persistentData.getCompound("lightanddarkmod");
        modData.putString("team", team);
        modData.putString("playerName", player.getGameProfile().getName());
        persistentData.put("lightanddarkmod", modData);
    }
    
        // 广播更新给所有客户端
        ServerEventHandler.broadcastTeamUpdate();
    }

    /**
     * 获取所有队伍的玩家UUID映射
     */
    public static Map<String, Set<UUID>> getTeamPlayerUUIDs() {
        return new HashMap<>(teamPlayers);
    }

    public static void setPlayerTeamByUUID(UUID playerUUID, String teamId) {
    playerTeams.put(playerUUID, teamId);
    
    if (!teamId.equals("none")) {
        if (!teamPlayers.containsKey(teamId)) {
            teamPlayers.put(teamId, new HashSet<>());
        }
        teamPlayers.get(teamId).add(playerUUID);
    }
    }

    public static Map<UUID, String> getAllPlayerTeams() {
    return new HashMap<>(playerTeams);
    }
}