package com.mc1124.lightanddark.client.gui;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class TeamInfoScreen extends Screen {
    private int imageWidth = 350;
    private int imageHeight = 220;
    private String playerTeam;
    private EditBox messageField;
    private List<TeamManager.TeamMessage> chatMessages;
    private int scrollOffset = 0;

    public TeamInfoScreen(String team) {
        super(Component.translatable("gui." + LightAndDarkMod.MOD_ID + ".team_info"));
        this.playerTeam = team;
        this.chatMessages = TeamManager.getRecentTeamMessages(team, 15);
    }

    @Override
protected void init() {
    super.init();
    int leftPos = (this.width - this.imageWidth) / 2;
    int topPos = (this.height - this.imageHeight) / 2;
    
    // 消息输入框
    this.messageField = new EditBox(this.font, leftPos + 10, topPos + 185, 250, 20, 
        Component.translatable("gui.lightanddarkmod.type_message"));
    this.messageField.setMaxLength(100);
    this.messageField.setHint(Component.literal("输入消息..."));
    this.addRenderableWidget(messageField);
    
    // 发送消息按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("📤"), 
        button -> sendTeamMessage()
    ).bounds(leftPos + 270, topPos + 185, 30, 20).build());
    
    // 聊天滚动按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("⬆"), 
        button -> scrollUp()
    ).bounds(leftPos + 310, topPos + 40, 20, 20).build());
    
    this.addRenderableWidget(Button.builder(
        Component.literal("⬇"), 
        button -> scrollDown()
    ).bounds(leftPos + 310, topPos + 160, 20, 20).build());
    
    // 返回按钮 - 右上角
    this.addRenderableWidget(Button.builder(
        Component.literal("❌"), 
        button -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(new TeamSelectionScreen());
            }
        }
    ).bounds(leftPos + imageWidth - 25, topPos + 5, 20, 20).build());
    
    // 刷新按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("🔄"), 
        button -> refreshMessages()
    ).bounds(leftPos + imageWidth - 50, topPos + 5, 20, 20).build());
    
    // 删除队伍按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("🗑️"), 
        button -> deleteTeam()
    ).bounds(leftPos + imageWidth - 75, topPos + 5, 20, 20).build());
    
    this.setInitialFocus(messageField);
    }

    private void deleteTeam() {
    if (this.minecraft != null && this.minecraft.player != null) {
        String teamName = TeamManager.getTeamName(playerTeam);
        
        // 发送删除队伍请求到服务器
        sendDeleteTeamToServer(playerTeam, teamName);
    }
    }

    private void sendDeleteTeamToServer(String teamId, String teamName) {
    if (this.minecraft.getConnection() != null && com.mc1124.lightanddark.network.NetworkHandler.INSTANCE != null) {
        // 发送删除队伍网络包到服务器
        com.mc1124.lightanddark.network.DeleteTeamPacket packet = new com.mc1124.lightanddark.network.DeleteTeamPacket(teamId);
        com.mc1124.lightanddark.network.NetworkHandler.INSTANCE.sendToServer(packet);
        
        // 临时在客户端删除队伍（服务器确认后会覆盖）
        TeamManager.deleteTeam(teamId);
        TeamManager.setPlayerTeam(this.minecraft.player, "none");
        
        // 返回到队伍选择界面
        this.minecraft.setScreen(new TeamSelectionScreen());
    } else {
        // 单机模式或网络不可用，使用本地删除
        boolean success = TeamManager.deleteTeam(teamId);
        if (success) {
            this.minecraft.player.sendSystemMessage(Component.literal("🗑️ 成功删除队伍: " + teamName));
            TeamManager.setPlayerTeam(this.minecraft.player, "none");
            this.minecraft.setScreen(new TeamSelectionScreen());
        } else {
            this.minecraft.player.sendSystemMessage(Component.literal("❌ 删除失败: 队伍不存在"));
        }
    }
    }

    private void scrollUp() {
        if (scrollOffset > 0) {
            scrollOffset--;
        }
    }

    private void scrollDown() {
        int maxScroll = Math.max(0, chatMessages.size() - 10);
        if (scrollOffset < maxScroll) {
            scrollOffset++;
        }
    }

    private void refreshMessages() {
        chatMessages = TeamManager.getRecentTeamMessages(playerTeam, 15);
        scrollOffset = 0;
    }

    private void sendTeamMessage() {
        if (this.minecraft == null || this.minecraft.player == null) {
            return;
        }
        
        String message = messageField.getValue().trim();
        if (message.isEmpty()) {
            return;
        }
        
        String teamName = TeamManager.getTeamName(playerTeam);
        String playerName = this.minecraft.player.getDisplayName().getString();
        
        Component teamMessage = Component.literal("[" + teamName + "] " + playerName + ": " + message);
        this.minecraft.player.sendSystemMessage(teamMessage);
        
        TeamManager.addTeamMessage(playerTeam, playerName, message);
        
        // 刷新消息列表
        refreshMessages();
        
        // 自动滚动到底部
        scrollOffset = Math.max(0, chatMessages.size() - 10);
        
        // 清空输入框
        messageField.setValue("");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        
        // 绘制背景
        drawBackground(guiGraphics, leftPos, topPos);
        
        // 绘制标题和状态
        drawHeader(guiGraphics, leftPos, topPos);
        
        // 绘制聊天区域
        drawChatArea(guiGraphics, leftPos, topPos);
        
        // 绘制聊天消息
        drawChatMessages(guiGraphics, leftPos, topPos);
        
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawBackground(GuiGraphics guiGraphics, int leftPos, int topPos) {
        // 主背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xCC1A1A1A);
        guiGraphics.fill(leftPos + 2, topPos + 2, leftPos + imageWidth - 2, topPos + imageHeight - 2, 0xCC2D2D2D);
        
        // 标题栏背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + 25, 0x6644AAFF);
        
        // 聊天区域背景
        guiGraphics.fill(leftPos + 10, topPos + 35, leftPos + imageWidth - 10, topPos + 180, 0x33333333);
        
        // 输入区域背景
        guiGraphics.fill(leftPos + 10, topPos + 185, leftPos + imageWidth - 10, topPos + 210, 0x444444);
    }

    private void drawHeader(GuiGraphics guiGraphics, int leftPos, int topPos) {
        String teamName = TeamManager.getTeamName(playerTeam);
        int memberCount = TeamManager.getTeamPlayerCount(playerTeam);
        
        // 队伍名称
        guiGraphics.drawString(this.font, "🏹 " + teamName, leftPos + 10, topPos + 8, 0xFFFFFF, false);
        
        // 成员数量
        guiGraphics.drawString(this.font, "👥 " + memberCount + " 成员", leftPos + 120, topPos + 8, 0xCCCCCC, false);
        
        // 在线状态
        guiGraphics.drawString(this.font, "🟢 在线", leftPos + 200, topPos + 8, 0x44FF44, false);
    }

    private void drawChatArea(GuiGraphics guiGraphics, int leftPos, int topPos) {
        // 聊天区域边框
        guiGraphics.fill(leftPos + 10, topPos + 35, leftPos + imageWidth - 10, topPos + 180, 0xFF222222);
        guiGraphics.fill(leftPos + 11, topPos + 36, leftPos + imageWidth - 11, topPos + 179, 0xFF111111);
        
        // 输入区域边框
        guiGraphics.fill(leftPos + 10, topPos + 185, leftPos + imageWidth - 10, topPos + 210, 0xFF333333);
        guiGraphics.fill(leftPos + 11, topPos + 186, leftPos + imageWidth - 11, topPos + 209, 0xFF222222);
    }

    private void drawChatMessages(GuiGraphics guiGraphics, int leftPos, int topPos) {
        int startY = topPos + 40;
        int maxMessages = 10;
        
        int startIndex = Math.max(0, chatMessages.size() - maxMessages - scrollOffset);
        int endIndex = Math.min(chatMessages.size(), startIndex + maxMessages);
        
        // 如果没有消息，显示提示
        if (chatMessages.isEmpty()) {
            guiGraphics.drawCenteredString(this.font, "💬 还没有消息，开始聊天吧！", 
                leftPos + imageWidth / 2, topPos + 80, 0xAAAAAA);
            return;
        }
        
        for (int i = startIndex; i < endIndex; i++) {
            TeamManager.TeamMessage message = chatMessages.get(i);
            int yPos = startY + (i - startIndex) * 13;
            
            // 格式化消息
            String formattedMessage = "💬 " + message.playerName + ": " + message.message;
            
            // 限制消息长度
            if (formattedMessage.length() > 40) {
                formattedMessage = formattedMessage.substring(0, 40) + "...";
            }
            
            guiGraphics.drawString(this.font, formattedMessage, leftPos + 15, yPos, 0xFFFFFF, false);
        }
        
        // 显示滚动提示
        if (scrollOffset > 0) {
            guiGraphics.drawString(this.font, "⬆ 更多", leftPos + 315, topPos + 38, 0xAAAAAA, false);
        }
        
        if (scrollOffset < Math.max(0, chatMessages.size() - maxMessages)) {
            guiGraphics.drawString(this.font, "⬇ 更多", leftPos + 315, topPos + 182, 0xAAAAAA, false);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 && this.getFocused() == messageField) {
            sendTeamMessage();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}