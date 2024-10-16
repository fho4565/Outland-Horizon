package com.arc.outland_horizon.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ChatUtils {
    public static void sendSimpleMessageToPlayer(Player player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    public static void sendSimpleMessageToPlayer(Player player, Component message) {
        player.sendSystemMessage(message);
    }

    public static void sendMessageToAllPlayers(String message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(Component.literal(message)));
    }
    public static void sendMessageToAllPlayers(Component message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(message));
    }
}
