package com.mc1124.lightanddark.commands;

import com.mc1124.lightanddark.system.TeamManager;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TeamCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("team")
            .requires(source -> source.hasPermission(2))
            .then(Commands.literal("create")
                .then(Commands.argument("teamId", StringArgumentType.string())
                    .then(Commands.argument("teamName", StringArgumentType.greedyString())
                        .executes(context -> createTeam(
                            context.getSource(), 
                            StringArgumentType.getString(context, "teamId"),
                            StringArgumentType.getString(context, "teamName")
                        ))
                    )
                )
            )
            .then(Commands.literal("list")
                .executes(context -> listTeams(context.getSource()))
            )
            .then(Commands.literal("delete")
                .then(Commands.argument("teamId", StringArgumentType.string())
                    .executes(context -> deleteTeam(
                        context.getSource(),
                        StringArgumentType.getString(context, "teamId")
                    ))
                )
            )
        );
        
        dispatcher.register(Commands.literal("jointeam")
            .then(Commands.argument("teamId", StringArgumentType.string())
                .executes(context -> joinTeam(
                    context.getSource(),
                    StringArgumentType.getString(context, "teamId")
                ))
            )
        );
    }
    
    private static int createTeam(CommandSourceStack source, String teamId, String teamName) {
        boolean success = TeamManager.createTeam(teamId, teamName);
        if (success) {
            source.sendSuccess(() -> Component.literal("成功创建队伍: " + teamName + " (ID: " + teamId + ")"), true);
        } else {
            source.sendFailure(Component.literal("创建失败: 队伍ID " + teamId + " 已存在"));
        }
        return Command.SINGLE_SUCCESS;
    }
    
    private static int listTeams(CommandSourceStack source) {
        var teams = TeamManager.getCustomTeams();
        if (teams.isEmpty()) {
            source.sendSuccess(() -> Component.literal("当前没有任何队伍"), false);
        } else {
            source.sendSuccess(() -> Component.literal("当前队伍列表:"), false);
            for (var entry : teams.entrySet()) {
                source.sendSuccess(() -> Component.literal(" - " + entry.getValue() + " (ID: " + entry.getKey() + ")"), false);
            }
        }
        return Command.SINGLE_SUCCESS;
    }
    
    private static int deleteTeam(CommandSourceStack source, String teamId) {
        boolean success = TeamManager.deleteTeam(teamId);
        if (success) {
            source.sendSuccess(() -> Component.literal("成功删除队伍: " + teamId), true);
        } else {
            source.sendFailure(Component.literal("删除失败: 队伍 " + teamId + " 不存在"));
        }
        return Command.SINGLE_SUCCESS;
    }
    
    private static int joinTeam(CommandSourceStack source, String teamId) {
        if (source.getPlayer() == null) {
            source.sendFailure(Component.literal("只有玩家可以执行此命令"));
            return 0;
        }
        
        String teamName = TeamManager.getTeamName(teamId);
        if (!teamName.equals("未知队伍")) {
            TeamManager.setPlayerTeam(source.getPlayer(), teamId);
            source.sendSuccess(() -> Component.literal("你已加入: " + teamName), true);
        } else {
            source.sendFailure(Component.literal("加入失败: 队伍 " + teamId + " 不存在"));
        }
        return Command.SINGLE_SUCCESS;
    }
}