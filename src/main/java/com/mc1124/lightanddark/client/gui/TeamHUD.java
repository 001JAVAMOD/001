package com.mc1124.lightanddark.client.gui;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.system.TeamManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class TeamHUD {
    private static final TeamHUD INSTANCE = new TeamHUD();
    private List<TeamPlayer> teamPlayers = new ArrayList<>();
    private long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL = 1000; // 1秒更新一次

    public static TeamHUD getInstance() {
        return INSTANCE;
    }

    private static class TeamPlayer {
        public final String name;
        public final UUID uuid;
        public final boolean isCurrentPlayer;
        
        public TeamPlayer(String name, UUID uuid, boolean isCurrentPlayer) {
            this.name = name;
            this.uuid = uuid;
            this.isCurrentPlayer = isCurrentPlayer;
        }
    }

    public void render(GuiGraphics guiGraphics, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        // 定期更新玩家列表
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > UPDATE_INTERVAL) {
            updateTeamPlayers(minecraft.player);
            lastUpdateTime = currentTime;
        }

        // 如果没有队伍成员，不显示
        if (teamPlayers.isEmpty()) return;

        int screenLeft = 10;  // 距离屏幕左边10像素
        int screenTop = 50;   // 距离屏幕顶部50像素，避免遮挡其他HUD元素
        
        int panelWidth = 120;
        int panelHeight = teamPlayers.size() * 20 + 25;

        // 绘制半透明背景
        guiGraphics.fill(screenLeft, screenTop, screenLeft + panelWidth, screenTop + panelHeight, 0x99222222);
        guiGraphics.fill(screenLeft + 1, screenTop + 1, screenLeft + panelWidth - 1, screenTop + panelHeight - 1, 0x66111111);

        // 绘制标题
        guiGraphics.drawString(minecraft.font, "队伍成员 (" + teamPlayers.size() + ")", screenLeft + 5, screenTop + 5, 0xFFFFFF, false);

        // 绘制玩家列表
        int yOffset = screenTop + 25;
        for (TeamPlayer teamPlayer : teamPlayers) {
            drawPlayerEntry(guiGraphics, screenLeft + 5, yOffset, teamPlayer);
            yOffset += 20;
        }
    }

    private void updateTeamPlayers(Player player) {
        teamPlayers.clear();
        
        String currentTeam = TeamManager.getPlayerTeam(player);
        if (currentTeam.equals("none")) {
            return; // 没有队伍时不显示
        }

        Map<UUID, String> teamPlayersData = TeamManager.getTeamPlayers(currentTeam);
        
        for (Map.Entry<UUID, String> entry : teamPlayersData.entrySet()) {
            UUID playerUUID = entry.getKey();
            String playerName = entry.getValue();
            boolean isCurrentPlayer = playerUUID.equals(player.getUUID());
            
            teamPlayers.add(new TeamPlayer(playerName, playerUUID, isCurrentPlayer));
        }

        // 如果当前玩家不在队伍中，添加当前玩家
        if (!teamPlayers.stream().anyMatch(p -> p.uuid.equals(player.getUUID()))) {
            teamPlayers.add(new TeamPlayer(player.getGameProfile().getName(), player.getUUID(), true));
        }

        // 按玩家名称排序
        teamPlayers.sort(Comparator.comparing(p -> p.name));
    }

    private void drawPlayerEntry(GuiGraphics guiGraphics, int x, int y, TeamPlayer teamPlayer) {
        Minecraft minecraft = Minecraft.getInstance();
        int avatarSize = 16;
        
        // 绘制半透明头像背景
        guiGraphics.fill(x, y, x + avatarSize, y + avatarSize, 0x66444444);
        
        // 绘制玩家脸部
        drawPlayerFace(guiGraphics, x, y, avatarSize, teamPlayer);
        
        // 绘制玩家名字
        int nameColor = teamPlayer.isCurrentPlayer ? 0x44AAFF : 0xFFFFFF;
        guiGraphics.drawString(minecraft.font, teamPlayer.name, x + avatarSize + 3, y + 4, nameColor, false);
    }

    private void drawPlayerFace(GuiGraphics guiGraphics, int x, int y, int size, TeamPlayer teamPlayer) {
        try {
            ResourceLocation skinTexture = DefaultPlayerSkin.getDefaultSkin(teamPlayer.uuid);
            
            int faceU = 8;
            int faceV = 8;
            int faceWidth = 8;
            int faceHeight = 8;
            
            // 使用半透明绘制
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 0.8f);
            guiGraphics.blit(
                skinTexture,
                x, y,
                size, size,
                faceU, faceV,
                faceWidth, faceHeight,
                64, 64
            );
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            
            // 添加半透明边框
            guiGraphics.fill(x, y, x + size, y + 1, 0x66666666);
            guiGraphics.fill(x, y + size - 1, x + size, y + size, 0x66666666);
            guiGraphics.fill(x, y, x + 1, y + size, 0x66666666);
            guiGraphics.fill(x + size - 1, y, x + size, y + size, 0x66666666);
            
        } catch (Exception e) {
            drawFallbackAvatar(guiGraphics, x, y, size, teamPlayer);
        }
    }

    private void drawFallbackAvatar(GuiGraphics guiGraphics, int x, int y, int size, TeamPlayer teamPlayer) {
        Minecraft minecraft = Minecraft.getInstance();
        char firstChar = teamPlayer.name.charAt(0);
        int bgColor = getColorFromName(teamPlayer.name);
        
        // 使用半透明背景
        int transparentBg = (bgColor & 0x00FFFFFF) | 0x99000000;
        guiGraphics.fill(x, y, x + size, y + size, transparentBg);
        
        int textColor = getContrastColor(bgColor);
        guiGraphics.drawCenteredString(minecraft.font, String.valueOf(firstChar), x + size / 2, y + (size - 8) / 2, textColor);
        
        // 半透明边框
        guiGraphics.fill(x, y, x + size, y + 1, 0x66666666);
        guiGraphics.fill(x, y + size - 1, x + size, y + size, 0x66666666);
        guiGraphics.fill(x, y, x + 1, y + size, 0x66666666);
        guiGraphics.fill(x + size - 1, y, x + size, y + size, 0x66666666);
    }

    private int getColorFromName(String name) {
        int hash = name.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = hash & 0x0000FF;
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    private int getContrastColor(int bgColor) {
        int r = (bgColor >> 16) & 0xFF;
        int g = (bgColor >> 8) & 0xFF;
        int b = bgColor & 0xFF;
        double brightness = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
        return brightness > 0.5 ? 0xFF000000 : 0xFFFFFFFF;
    }
}