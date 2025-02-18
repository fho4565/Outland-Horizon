package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.develop.ModLang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ChatUtils {
    public static void singlePlayer(Player player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    public static void singlePlayer(Player player, Component message) {
        player.sendSystemMessage(message);
    }

    public static void allPlayers(String message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(Component.literal(message)));
    }

    public static void allPlayers(Component message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendSystemMessage(message));
    }

    public static MutableComponent translatable(String key, Object... args) {
        MutableComponent translatable = Component.translatable(key, args);
        if (translatable.plainCopy().getString().equals(key)) {
            ModLang.lang.add(key);
        }
        return translatable;
    }
}
