package com.mc1124.lightanddark.client.gui;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TeamSelectionScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
            LightAndDarkMod.MOD_ID, "textures/gui/team_selection.png");
    
    private int imageWidth = 300;
    private int imageHeight = 180;
    private boolean hasTeam = false;
    private String currentTeam = "";

    public TeamSelectionScreen() {
        super(Component.translatable("gui." + LightAndDarkMod.MOD_ID + ".team_selection"));
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        int centerX = leftPos + imageWidth / 2;
        
        // 检查玩家是否已经加入了队伍
        if (this.minecraft != null && this.minecraft.player != null) {
            this.currentTeam = TeamManager.getPlayerTeam(this.minecraft.player);
            this.hasTeam = !this.currentTeam.equals("none");
        }
        
        // 获取所有自定义队伍
        Map<String, String> customTeams = TeamManager.getCustomTeams();
        
        if (hasTeam) {
            // 已加入队伍的界面
            initJoinedTeamUI(leftPos, topPos, centerX, customTeams);
        } else {
            // 未加入队伍的界面
            initTeamSelectionUI(leftPos, topPos, centerX, customTeams);
        }
    }

    private void initJoinedTeamUI(int leftPos, int topPos, int centerX, Map<String, String> customTeams) {
    String currentTeamName = TeamManager.getTeamName(currentTeam);
    
    // 当前队伍信息按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("📋 " + currentTeamName), 
        button -> showTeamInfo(currentTeam)
    ).bounds(centerX - 120, topPos + 50, 240, 30).build());
    
    // 删除当前队伍按钮（只有创建者或管理员可以删除）
    this.addRenderableWidget(Button.builder(
        Component.literal("🗑️ 删除队伍"), 
        button -> deleteCurrentTeam()
    ).bounds(centerX - 60, topPos + 85, 120, 20).build());
    
    // 显示其他可加入的队伍
    int yOffset = 115;
    int otherTeamCount = 0;
    for (Map.Entry<String, String> entry : customTeams.entrySet()) {
        if (!entry.getKey().equals(currentTeam)) {
            this.addRenderableWidget(Button.builder(
                Component.literal("🔄 " + entry.getValue()), 
                button -> switchTeam(entry.getKey())
            ).bounds(centerX - 100, topPos + yOffset, 200, 20).build());
            yOffset += 25;
            otherTeamCount++;
        }
    }
    
    // 如果没有其他队伍，显示提示
    if (otherTeamCount == 0) {
        this.addRenderableWidget(Button.builder(
            Component.literal("暂无其他队伍"), 
            button -> {}
        ).bounds(centerX - 100, topPos + yOffset, 200, 20).build()).active = false;
        yOffset += 25;
    }
    
    // 创建新队伍按钮 - 检查数量限制
    int currentCount = TeamManager.getCurrentTeamCount();
    int maxCount = TeamManager.getMaxTeams();
    
    Button createButton = Button.builder(
        Component.literal("✨ 创建新队伍 (" + currentCount + "/" + maxCount + ")"), 
        button -> createNewTeam()
    ).bounds(centerX - 100, topPos + yOffset + 5, 200, 20).build();
    
    // 如果达到最大数量，禁用创建按钮
    if (currentCount >= maxCount) {
        createButton.active = false;
    }
    
    this.addRenderableWidget(createButton);
    
    // 关闭按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("❌ 关闭"), 
        button -> this.onClose()
    ).bounds(centerX - 40, topPos + 160, 80, 20).build());
}

    private void deleteCurrentTeam() {
    if (this.minecraft != null && this.minecraft.player != null) {
        String teamName = TeamManager.getTeamName(currentTeam);
        
        // 发送删除队伍请求到服务器
        sendDeleteTeamToServer(currentTeam, teamName);
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
        
        // 刷新界面
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

    private void initTeamSelectionUI(int leftPos, int topPos, int centerX, Map<String, String> customTeams) {
    // 显示所有可加入的队伍
    int yOffset = 60;
    int currentCount = TeamManager.getCurrentTeamCount();
    int maxCount = TeamManager.getMaxTeams();
    
    if (customTeams.isEmpty()) {
        // 如果没有队伍，显示提示
        this.addRenderableWidget(Button.builder(
            Component.literal("暂无可用队伍"), 
            button -> {}
        ).bounds(centerX - 100, topPos + yOffset, 200, 20).build()).active = false;
        yOffset += 30;
        
        this.addRenderableWidget(Button.builder(
            Component.literal("点击下方创建第一个队伍"), 
            button -> {}
        ).bounds(centerX - 120, topPos + yOffset, 240, 20).build()).active = false;
        yOffset += 25;
    } else {
        for (Map.Entry<String, String> entry : customTeams.entrySet()) {
            this.addRenderableWidget(Button.builder(
                Component.literal("✅ " + entry.getValue()), 
                button -> joinTeam(entry.getKey())
            ).bounds(centerX - 100, topPos + yOffset, 200, 20).build());
            yOffset += 25;
        }
    }
    
    // 创建新队伍按钮 - 检查数量限制
    Button createButton = Button.builder(
        Component.literal("✨ 创建新队伍 (" + currentCount + "/" + maxCount + ")"), 
        button -> createNewTeam()
    ).bounds(centerX - 100, topPos + yOffset + 10, 200, 20).build();
    
    // 如果达到最大数量，禁用创建按钮
    if (currentCount >= maxCount) {
        createButton.active = false;
    }
    
    this.addRenderableWidget(createButton);
    
    // 关闭按钮
    this.addRenderableWidget(Button.builder(
        Component.literal("❌ 关闭"), 
        button -> this.onClose()
    ).bounds(centerX - 40, topPos + 150, 80, 20).build());
    }

    private void joinTeam(String team) {
        if (this.minecraft != null && this.minecraft.player != null) {
            String teamName = TeamManager.getTeamName(team);
            String message = Component.translatable("message.lightanddarkmod.joined_team", teamName).getString();
            
            this.minecraft.player.sendSystemMessage(Component.literal("🎉 " + message));
            
            // 设置玩家队伍
            TeamManager.setPlayerTeam(this.minecraft.player, team);
            
            // 刷新界面
            this.minecraft.setScreen(new TeamSelectionScreen());
        }
    }

    private void switchTeam(String newTeam) {
        if (this.minecraft != null && this.minecraft.player != null) {
            String oldTeamName = TeamManager.getTeamName(TeamManager.getPlayerTeam(this.minecraft.player));
            String newTeamName = TeamManager.getTeamName(newTeam);
            String message = Component.translatable("message.lightanddarkmod.switched_team", oldTeamName, newTeamName).getString();
        
            this.minecraft.player.sendSystemMessage(Component.literal("🔄 " + message));
        
            // 设置玩家队伍
            TeamManager.setPlayerTeam(this.minecraft.player, newTeam);
        
            // 刷新界面
            this.minecraft.setScreen(new TeamSelectionScreen());
        }
    }

    private void createNewTeam() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new CreateTeamScreen());
        }
    }

    private void showTeamInfo(String team) {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new TeamInfoScreen(team));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics);
    
    int leftPos = (this.width - this.imageWidth) / 2;
    int topPos = (this.height - this.imageHeight) / 2;
    int centerX = this.width / 2;

    // 绘制背景
    drawBackground(guiGraphics, leftPos, topPos);
    
    // 绘制标题
    if (hasTeam) {
        guiGraphics.drawCenteredString(this.font, "🎯 队伍管理", centerX, topPos + 20, 0x44AAFF);
        String currentTeamName = TeamManager.getTeamName(currentTeam);
        guiGraphics.drawCenteredString(this.font, "当前队伍: " + currentTeamName, centerX, topPos + 35, 0xFFFFFF);
    } else {
        guiGraphics.drawCenteredString(this.font, "🏹 选择队伍", centerX, topPos + 20, 0x44AAFF);
        guiGraphics.drawCenteredString(this.font, "请选择一个队伍加入", centerX, topPos + 35, 0xCCCCCC);
    }
    
    // 显示队伍数量信息
    int currentCount = TeamManager.getCurrentTeamCount();
    int maxCount = TeamManager.getMaxTeams();
    String countInfo = "📊 队伍数量: " + currentCount + "/" + maxCount;
    guiGraphics.drawCenteredString(this.font, countInfo, centerX, topPos + imageHeight - 25, 0xAAAAAA);
    
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawBackground(GuiGraphics guiGraphics, int leftPos, int topPos) {
        // 主背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xCC1A1A1A);
        guiGraphics.fill(leftPos + 2, topPos + 2, leftPos + imageWidth - 2, topPos + imageHeight - 2, 0xCC2D2D2D);
        
        // 标题栏背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + 25, 0x6644AAFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}