package com.arc.outland_horizon;

import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ArmorSuits {
    public static ConcurrentLinkedDeque<ArmorSuit> armorSuits = new ConcurrentLinkedDeque<>();

    public static void tick(Player player) {
        for (ArmorSuit armorSuit : armorSuits) {
            if (armorSuit.isComplete(player)) {
                if (!armorSuit.contains(player)) {
                    armorSuit.onArmorSuitCompleted(player);
                    armorSuit.players.add(player.getUUID());
                }
                armorSuit.onArmorSuitTick(player);
            } else {
                if (armorSuit.contains(player)) {
                    armorSuit.onArmorSuitUnset(player);
                    armorSuit.players.remove(player.getUUID());
                }
            }
        }
    }
}
