package com.mc1124.lightanddark.system;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.*;
import java.util.Map;

public class TeamDataManager {
    private static final String TEAM_DATA_FILE = "lightanddark_teams.dat";

    public static void saveTeams(MinecraftServer server) {
        try {
            File worldDir = server.getWorldPath(LevelResource.ROOT).toFile();
            File dataFile = new File(worldDir, TEAM_DATA_FILE);
            
            CompoundTag rootTag = new CompoundTag();
            
            // 保存队伍数据
            CompoundTag teamsTag = new CompoundTag();
            Map<String, String> customTeams = TeamManager.getCustomTeams();
            for (Map.Entry<String, String> entry : customTeams.entrySet()) {
                teamsTag.putString(entry.getKey(), entry.getValue());
            }
            rootTag.put("teams", teamsTag);
            
            // 保存玩家队伍数据 - 使用正确的类型
            CompoundTag playerTeamsTag = new CompoundTag();
            // 这里需要添加一个方法来获取所有玩家的队伍数据
            // 暂时先保存一个空的数据
            rootTag.put("playerTeams", playerTeamsTag);
            
            // 写入文件
            try (FileOutputStream fos = new FileOutputStream(dataFile);
                 DataOutputStream dos = new DataOutputStream(fos)) {
                net.minecraft.nbt.NbtIo.write(rootTag, dos);
            }
            
        } catch (Exception e) {
            System.err.println("保存队伍数据失败: " + e.getMessage());
        }
    }

    public static void loadTeams(MinecraftServer server) {
        try {
            File worldDir = server.getWorldPath(LevelResource.ROOT).toFile();
            File dataFile = new File(worldDir, TEAM_DATA_FILE);
            
            if (!dataFile.exists()) {
                return;
            }
            
            CompoundTag rootTag;
            try (FileInputStream fis = new FileInputStream(dataFile);
                 DataInputStream dis = new DataInputStream(fis)) {
                rootTag = net.minecraft.nbt.NbtIo.read(dis);
            }
            
            if (rootTag == null) {
                return;
            }
            
            // 加载队伍数据
            if (rootTag.contains("teams")) {
                CompoundTag teamsTag = rootTag.getCompound("teams");
                for (String teamId : teamsTag.getAllKeys()) {
                    String teamName = teamsTag.getString(teamId);
                    TeamManager.createTeam(teamId, teamName);
                }
            }
            
            // 加载玩家队伍数据
            if (rootTag.contains("playerTeams")) {
                CompoundTag playerTeamsTag = rootTag.getCompound("playerTeams");
                for (String playerUUIDStr : playerTeamsTag.getAllKeys()) {
                    try {
                    } catch (IllegalArgumentException e) {
                        System.err.println("无效的玩家UUID: " + playerUUIDStr);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("加载队伍数据失败: " + e.getMessage());
        }
    }
}