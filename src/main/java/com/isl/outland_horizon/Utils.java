package com.isl.outland_horizon;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();

    /**
     * @param playerName 玩家名字
     * @param objective  要获取的计分板名字
     */
    public static int getScore(MinecraftServer server, String playerName, Objective objective) {
        Scoreboard scoreboard = server.getScoreboard();
        if (!scoreboard.hasPlayerScore(playerName, objective)) {
            return 0;
        } else {
            Score score = scoreboard.getOrCreatePlayerScore(playerName, objective);
            return score.getScore();
        }
    }
    /**
     * 设置指定玩家在特定目标上的得分。
     * <p>
     * 此方法用于更新Minecraft游戏中的玩家得分。它首先从服务器获取Scoreboard对象，
     * 然后根据玩家名称和目标名称获取或创建一个Score对象，最后设置该Score对象的得分值。
     *
     * @param server Minecraft服务器实例，用于获取Scoreboard对象。
     * @param playerName 要设置得分的玩家名称。
     * @param objective 目标名称，玩家的得分将在这个目标上更新。
     * @param num 要设置的得分值。
     */
    public static void setScore(MinecraftServer server, String playerName, Objective objective, int num) {
        Scoreboard scoreboard = server.getScoreboard();
        Score score = scoreboard.getOrCreatePlayerScore(playerName, objective);
        score.setScore(num);
    }

    public static Tag getData(String resourceLocation) {
        ResourceLocation location = ResourceLocation.tryParse(resourceLocation);
        if(location == null){
            return new CompoundTag();
        }
        return currentServer.getCommandStorage().get(location);
    }

    public static void setData(String sourceResourceLocation, CompoundTag tag) {
        ResourceLocation location = ResourceLocation.tryParse(sourceResourceLocation);
        if(location == null){
            return;
        }
        currentServer.getCommandStorage().set(location, tag);
    }
    public static void sendSimpleMessageToPlayer(Player player, String message){
        player.sendSystemMessage(Component.literal(message));
    }
    public static void executeCommand(ServerPlayer player, String command,boolean mute) {
        if (command.equals("")){
            return;
        }
        if (player.getServer() == null){return;}
        CommandSourceStack commandSourceStack = player.createCommandSourceStack();
        player.getServer().getCommands().performPrefixedCommand(mute?commandSourceStack.withSuppressedOutput():commandSourceStack, command);
    }
}
