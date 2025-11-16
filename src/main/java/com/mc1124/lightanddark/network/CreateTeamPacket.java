package com.mc1124.lightanddark.network;

import com.mc1124.lightanddark.system.TeamManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CreateTeamPacket {
    private final String teamId;
    private final String teamName;

    public CreateTeamPacket(String teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public CreateTeamPacket(FriendlyByteBuf buf) {
        this.teamId = buf.readUtf();
        this.teamName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(teamId);
        buf.writeUtf(teamName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 服务器端处理创建队伍请求
            ServerPlayer player = context.getSender();
            if (player != null) {
                // 在服务器端创建队伍
                boolean success = TeamManager.createTeam(teamId, teamName);
                if (success) {
                    // 设置玩家队伍
                    TeamManager.setPlayerTeam(player, teamId);
                    player.sendSystemMessage(Component.literal("成功创建队伍: " + teamName));
                    
                    // 广播队伍更新给所有客户端
                    com.mc1124.lightanddark.server.ServerEventHandler.broadcastTeamUpdate();
                } else {
                    player.sendSystemMessage(Component.literal("❌ 创建失败: 队伍ID或名称已存在"));
                }
            }
        });
        return true;
    }
}