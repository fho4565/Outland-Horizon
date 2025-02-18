package com.arc.outland_horizon.core;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public abstract class ArmorSuit {
    public Item helmet, chestplate, leggings, boots;
    public String id;
    HashSet<UUID> players = new HashSet<>();

    public ArmorSuit(String id, Item helmet, Item chestplate, Item leggings, Item boots) {
        this.id = id;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public final boolean isComplete(Player player) {
        ItemStack item1 = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack item2 = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack item3 = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack item4 = player.getItemBySlot(EquipmentSlot.FEET);
        return item1.getItem() == helmet && item2.getItem() == chestplate && item3.getItem() == leggings && item4.getItem() == boots;
    }

    public boolean contains(Player player) {
        return players.contains(player.getUUID());
    }

    public void onArmorSuitCompleted(Player player) {
    }

    public abstract void onArmorSuitTick(Player player);

    public void onArmorSuitUnset(Player player) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArmorSuit armorSuit)) return false;
        return this.id.equals(armorSuit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(helmet, chestplate, leggings, boots);
    }
}
