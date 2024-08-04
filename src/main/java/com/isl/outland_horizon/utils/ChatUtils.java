package com.isl.outland_horizon.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChatUtils {
    public static void sendSimpleMessageToPlayer(Player player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    public static void sendSimpleMessageToPlayer(Player player, Component message) {
        player.sendSystemMessage(message);
    }
}
