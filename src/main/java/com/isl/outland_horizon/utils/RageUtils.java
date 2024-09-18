package com.isl.outland_horizon.utils;

import com.isl.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.world.entity.player.Player;

import static com.isl.outland_horizon.world.capability.ModCapabilities.getOhAttribute;
import static com.isl.outland_horizon.world.capability.ModCapabilities.serverSyncAttribute;

public class RageUtils {
    public static double getRage(Player player) {
        return getOhAttribute(player).getRage();
    }

    public static double getRageRecover(Player player) {
        return getOhAttribute(player).getRageRecover();
    }

    public static double getMaxRage(Player player) {
        return getOhAttribute(player).getMaxRage();
    }

    public static void recoverRage(Player player) {
        addRage(player, getRageRecover(player));
    }

    public static void addRage(Player player, double rage) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setRage(Math.min(attribute.getRage() + rage, getMaxRage(player)));
        serverSyncAttribute(player);
    }
    public static boolean removeRage(Player player, double amount) {
        OhAttribute attribute = getOhAttribute(player);
        if(amount >= attribute.getRage()){
            return false;
        }else{
            attribute.setRage(attribute.getRage() - amount);
            serverSyncAttribute(player);
            return true;
        }
    }

    public static void setRage(Player player, double rage) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setRage(Math.max(rage, 0));
        serverSyncAttribute(player);
    }

    public static void setRageRecover(Player player, double rageRecover) {
        OhAttribute attribute = getOhAttribute(player);
        attribute.setRageRecover(Math.max(rageRecover, 0));
        serverSyncAttribute(player);
    }

    public static void removeRageRecover(Player player,double amount) {
        OhAttribute attribute = getOhAttribute(player);
        if(amount >= attribute.getRageRecover()){
            attribute.setRageRecover(0.0D);
        }else{
            attribute.setRageRecover(attribute.getRageRecover() - amount);
        }
        serverSyncAttribute(player);
    }
}
