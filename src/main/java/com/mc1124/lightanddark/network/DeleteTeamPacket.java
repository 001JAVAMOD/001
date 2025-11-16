package com.mc1124.lightanddark.network;

import com.mc1124.lightanddark.system.TeamManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DeleteTeamPacket {
    private final String teamId;

    public DeleteTeamPacket(String teamId) {
        this.teamId = teamId;
    }

    public DeleteTeamPacket(FriendlyByteBuf buf) {
        this.teamId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(teamId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 服务器端处理删除队伍请求
            ServerPlayer player = context.getSender();
            if (player != null) {
                String teamName = TeamManager.getTeamName(teamId);
                
                // 在服务器端删除队伍
                boolean success = TeamManager.deleteTeam(teamId);
                if (success) {
                    player.sendSystemMessage(Component.literal("🗑️ 成功删除队伍: " + teamName));
                    
                    // 广播队伍更新给所有客户端
                    com.mc1124.lightanddark.server.ServerEventHandler.broadcastTeamUpdate();
                } else {
                    player.sendSystemMessage(Component.literal("❌ 删除失败: 队伍不存在"));
                }
            }
        });
        return true;
    }
}