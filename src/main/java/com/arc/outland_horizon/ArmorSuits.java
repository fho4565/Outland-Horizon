package com.arc.outland_horizon;

import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

    public static void init() {
        armorSuits.clear();
        armorSuits.add(new ArmorSuit("blood_stone", OHItems.Armor.BLOOD_STONE_HELMET.get(), OHItems.Armor.BLOOD_STONE_CHESTPLATE.get(), OHItems.Armor.BLOOD_STONE_LEGGINGS.get(), OHItems.Armor.BLOOD_STONE_BOOTS.get()) {
            @Override
            public void onArmorSuitTick(Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3, true, true));
            }
        });
    }
}
