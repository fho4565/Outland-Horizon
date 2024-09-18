package com.isl.outland_horizon.utils;

import com.isl.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.world.entity.player.Player;

import static com.isl.outland_horizon.world.capability.ModCapabilities.getOhAttribute;
import static com.isl.outland_horizon.world.capability.ModCapabilities.serverSyncAttribute;

public class ManaUtils {
    public static double getMana(Player player) {
        return getOhAttribute(player).getMana();
    }

    public static double getManaRecover(Player player) {
        return getOhAttribute(player).getManaRecover();
    }

    public static double getMaxMana(Player player) {
        return getOhAttribute(player).getMaxMana();
    }

    public static void recoverMana(Player player) {
        addMana(player, getManaRecover(player));
    }

    public static void addMana(Player player, double mana) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setMana(Math.min(attribute.getMana() + mana, getMaxMana(player)));
        serverSyncAttribute(player);
    }
    public static boolean removeMana(Player player, double amount) {
        OhAttribute attribute = getOhAttribute(player);
        if(amount >= attribute.getMana()){
            return false;
        }else{
            attribute.setMana(attribute.getMana() - amount);
            serverSyncAttribute(player);
            return true;
        }
    }

    public static void setMana(Player player, double mana) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setMana(Math.max(mana, 0));
        serverSyncAttribute(player);
    }

    public static void setManaRecover(Player player, double manaRecover) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setManaRecover(Math.max(manaRecover, 0));
        serverSyncAttribute(player);
    }

    public static void removeManaRecover(Player player,double amount) {
        OhAttribute attribute = getOhAttribute(player);
        if(amount >= attribute.getManaRecover()){
            attribute.setManaRecover(0.0D);
        }else{
            attribute.setManaRecover(attribute.getManaRecover() - amount);
        }
        serverSyncAttribute(player);
    }
}
