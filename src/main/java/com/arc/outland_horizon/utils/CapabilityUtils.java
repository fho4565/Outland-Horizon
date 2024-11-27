package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.world.entity.player.Player;

import static com.arc.outland_horizon.world.capability.ModCapabilities.getOhAttribute;
import static com.arc.outland_horizon.world.capability.ModCapabilities.serverSyncAttribute;

public class CapabilityUtils {
    public static class Mana {

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
            if (amount >= attribute.getMana()) {
                return false;
            } else {
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

        public static void removeManaRecover(Player player, double amount) {
            OhAttribute attribute = getOhAttribute(player);
            if (amount >= attribute.getManaRecover()) {
                attribute.setManaRecover(0.0D);
            } else {
                attribute.setManaRecover(attribute.getManaRecover() - amount);
            }
            serverSyncAttribute(player);
        }

        public static boolean isManaSufficient(Player player, double amount) {
            return getMana(player) >= amount;
        }

        public static boolean isManaFull(Player player) {
            return getMana(player) >= getMaxMana(player);
        }
    }

    public static class Rage {

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
            if (amount >= attribute.getRage()) {
                return false;
            } else {
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

        public static void removeRageRecover(Player player, double amount) {
            OhAttribute attribute = getOhAttribute(player);
            if (amount >= attribute.getRageRecover()) {
                attribute.setRageRecover(0.0D);
            } else {
                attribute.setRageRecover(attribute.getRageRecover() - amount);
            }
            serverSyncAttribute(player);
        }

        public static boolean isRageFull(Player player) {
            return getRage(player) >= getMaxRage(player);
        }
    }

    public static class Shield {

        public static double getShieldValue(Player player) {
            return getOhAttribute(player).getShieldValue();
        }

        public static void addShieldValue(Player player, double shieldValue) {
            OhAttribute attribute = getOhAttribute(player);
            attribute.setShieldValue(attribute.getShieldValue() + shieldValue);
            serverSyncAttribute(player);
        }

        public static void setShieldValue(Player player, double shieldValue) {
            OhAttribute attribute = getOhAttribute(player);
            attribute.setShieldValue(shieldValue);
            serverSyncAttribute(player);
        }

        public static boolean removeShieldValue(Player player, double amount) {
            OhAttribute attribute = getOhAttribute(player);
            if (amount >= attribute.getShieldValue()) {
                return false;
            } else {
                attribute.setShieldValue(attribute.getShieldValue() - amount);
                serverSyncAttribute(player);
                return true;
            }
        }
    }
}
