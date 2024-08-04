package com.isl.outland_horizon.utils;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import net.minecraft.world.entity.player.Player;

public class ManaUtils {
    public static double getMana(Player player) {
        return player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.getAttrValue("mana")).orElse(100.0);
    }

    public static void setMana(Player player, double mana) {
        double max = player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.get("mana").getMax()).orElse(100.0);
        if (mana >= max) {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", max));
        } else {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", mana));
        }
    }

    public static boolean addMana(Player player, double mana) {
        double max = player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.get("mana").getMax()).orElse(100.0);
        if (getMana(player) < max) {
            if (mana >= max) {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                        .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", max));
            } else {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                        .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", (double) ohAttributes.getAttrValue("mana") + mana));
            }
            return true;
        } else {
            return false;
        }
    }
    public static boolean recoverMana(Player player) {
        return addMana(player, getManaRecover(player));
    }

    public static boolean removeMana(Player player, double mana) {
        if (getMana(player) < mana) {
            return false;
        } else {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", (double) ohAttributes.getAttrValue("mana") - mana));
            return true;
        }
    }
    public static double getManaRecover(Player player) {
        return player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.getAttrValue("mana_recover")).orElse(0.1);
    }

    public static void setManaRecover(Player player, double mana_recover) {
        double max = player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.get("mana_recover").getMax()).orElse(0.1);
        if (mana_recover >= max) {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", max));
        } else {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", mana_recover));
        }
    }

    public static boolean addManaRecover(Player player, double mana_recover) {
        double max = player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                .map(ohAttributes -> (double) ohAttributes.get("mana_recover").getMax()).orElse(100.0);
        if (getMana(player) < max) {
            if (mana_recover >= max) {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                        .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", max));
            } else {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                        .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", (double) ohAttributes.getAttrValue("mana_recover") + mana_recover));
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeManaRecover(Player player, double mana_recover) {
        if (getMana(player) < mana_recover) {
            return false;
        } else {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE)
                    .ifPresent(ohAttributes -> ohAttributes.setAttrValue("mana", (double) ohAttributes.getAttrValue("mana_recover") - mana_recover));
            return true;
        }
    }
}
