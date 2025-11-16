package com.mc1124.lightanddark.client.gui;

import com.mc1124.lightanddark.LightAndDarkMod;
import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CreateTeamScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
            LightAndDarkMod.MOD_ID, "textures/gui/create_team.png");
    
    private int imageWidth = 280;
    private int imageHeight = 200;
    private EditBox teamIdField;
    private EditBox teamNameField;

    public CreateTeamScreen() {
        super(Component.translatable("gui." + LightAndDarkMod.MOD_ID + ".create_team"));
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        int centerX = leftPos + imageWidth / 2;
        
        // 队伍ID输入框
        this.teamIdField = new EditBox(this.font, centerX - 100, topPos + 60, 200, 20, Component.translatable("message.lightanddarkmod.team_id"));
        this.teamIdField.setMaxLength(16);
        this.teamIdField.setHint(Component.literal("输入队伍ID..."));
        this.addRenderableWidget(teamIdField);
        
        // 队伍名称输入框
        this.teamNameField = new EditBox(this.font, centerX - 100, topPos + 90, 200, 20, Component.translatable("message.lightanddarkmod.team_name"));
        this.teamNameField.setMaxLength(20);
        this.teamNameField.setHint(Component.literal("输入队伍名称..."));
        this.addRenderableWidget(teamNameField);
        
        // 创建队伍按钮
        this.addRenderableWidget(Button.builder(
            Component.literal("✨ 创建"), 
            button -> {
                System.out.println("创建队伍按钮被点击了！");
                createTeam();
            }
        ).bounds(centerX - 60, topPos + 120, 120, 20).build());
        
        // 返回按钮
        this.addRenderableWidget(Button.builder(
            Component.literal("↩ 返回"), 
            button -> {
                if (this.minecraft != null) {
                    this.minecraft.setScreen(new TeamSelectionScreen());
                }
            }
        ).bounds(centerX - 40, topPos + 150, 80, 20).build());
        
        // 设置初始焦点
        this.setInitialFocus(teamIdField);
    }

    private void createTeam() {
        System.out.println("createTeam方法被调用");
        
        if (this.minecraft == null || this.minecraft.player == null) {
            System.out.println("minecraft或player为null");
            return;
        }
        
        // 检查队伍数量限制
        if (TeamManager.getCurrentTeamCount() >= TeamManager.getMaxTeams()) {
            this.minecraft.player.sendSystemMessage(Component.literal("❌ 已达到最大队伍数量限制（" + TeamManager.getMaxTeams() + "个）！"));
            return;
        }
        
        String teamId = teamIdField.getValue().trim();
        String teamName = teamNameField.getValue().trim();
        
        System.out.println("输入的队伍ID: " + teamId);
        System.out.println("输入的队伍名称: " + teamName);
        
        if (teamId.isEmpty()) {
            System.out.println("队伍ID为空");
            this.minecraft.player.sendSystemMessage(Component.literal("❌ 队伍ID不能为空！"));
            return;
        }
        
        if (teamName.isEmpty()) {
            teamName = teamId;
            System.out.println("使用ID作为队伍名称: " + teamName);
        }
        
        // 首先在客户端本地检查是否重复
        if (TeamManager.getCustomTeams().containsKey(teamId)) {
            this.minecraft.player.sendSystemMessage(Component.literal("❌ 队伍ID已存在，请使用其他ID！"));
            return;
        }
        
        // 检查队伍名称是否重复
        for (String existingName : TeamManager.getCustomTeams().values()) {
            if (existingName.equals(teamName)) {
                this.minecraft.player.sendSystemMessage(Component.literal("❌ 队伍名称已存在，请使用其他名称！"));
                return;
            }
        }
        
        // 发送创建队伍请求到服务器
        sendCreateTeamToServer(teamId, teamName);
    }

    private void sendCreateTeamToServer(String teamId, String teamName) {
        if (this.minecraft.getConnection() != null && com.mc1124.lightanddark.network.NetworkHandler.INSTANCE != null) {
            // 发送网络包到服务器
            com.mc1124.lightanddark.network.CreateTeamPacket packet = new com.mc1124.lightanddark.network.CreateTeamPacket(teamId, teamName);
            com.mc1124.lightanddark.network.NetworkHandler.INSTANCE.sendToServer(packet);
            
            // 临时在客户端创建队伍（服务器确认后会覆盖）
            TeamManager.createTeam(teamId, teamName);
            TeamManager.setPlayerTeam(this.minecraft.player, teamId);
            
            // 切换到队伍信息界面
            if (this.minecraft != null) {
                this.minecraft.setScreen(new TeamInfoScreen(teamId));
            }
        } else {
            // 单机模式或网络不可用，使用本地创建
            boolean success = TeamManager.createTeam(teamId, teamName);
            if (success) {
                this.minecraft.player.sendSystemMessage(Component.literal("🎉 成功创建队伍: " + teamName));
                TeamManager.setPlayerTeam(this.minecraft.player, teamId);
                if (this.minecraft != null) {
                    this.minecraft.setScreen(new TeamInfoScreen(teamId));
                }
            } else {
                this.minecraft.player.sendSystemMessage(Component.literal("❌ 创建失败: 队伍ID或名称已存在"));
            }
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
        
        // 绘制标题和说明
        drawInstructions(guiGraphics, leftPos, topPos, centerX);
        
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawBackground(GuiGraphics guiGraphics, int leftPos, int topPos) {
        // 主背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xCC1A1A1A);
        guiGraphics.fill(leftPos + 2, topPos + 2, leftPos + imageWidth - 2, topPos + imageHeight - 2, 0xCC2D2D2D);
        
        // 输入区域背景
        guiGraphics.fill(leftPos + 20, topPos + 50, leftPos + imageWidth - 20, topPos + 115, 0x334477AA);
    }

    private void drawInstructions(GuiGraphics guiGraphics, int leftPos, int topPos, int centerX) {
        // 标题
        guiGraphics.drawCenteredString(this.font, "✨ 创建新队伍", centerX, topPos + 20, 0x44AAFF);
        
        // 队伍数量信息
        int currentCount = TeamManager.getCurrentTeamCount();
        int maxCount = TeamManager.getMaxTeams();
        String countInfo = "📊 队伍数量: " + currentCount + "/" + maxCount;
        guiGraphics.drawCenteredString(this.font, countInfo, centerX, topPos + 35, 0xCCCCCC);
        
        // 如果达到最大数量，显示提示
        if (currentCount >= maxCount) {
            guiGraphics.drawCenteredString(this.font, "❌ 已达到最大队伍数量限制", centerX, topPos + 45, 0xFF5555);
        }
        
        // 输入框标签
        guiGraphics.drawString(this.font, "🔑 队伍ID", centerX - 100, topPos + 50, 0xFFFFFF, false);
        guiGraphics.drawString(this.font, "🏷️ 队伍名称", centerX - 100, topPos + 80, 0xFFFFFF, false);
        
        // 提示文字
        guiGraphics.drawCenteredString(this.font, "💡 队伍ID用于内部识别，不可重复", centerX, topPos + 115, 0xAAAAAA);
        guiGraphics.drawCenteredString(this.font, "💡 队伍名称用于显示，建议使用中文", centerX, topPos + 130, 0xAAAAAA);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}