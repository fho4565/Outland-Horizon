package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class FrequencyVariation extends Gun {
    public FrequencyVariation() {
        super(375, 10, Ingredient.of(OHItems.Material.VOID_CRYSTAL.get()), new Properties().rarity(Rarity.UNCOMMON));
    }


    @Override
    public int getBulletMaxAge() {
        return 10;
    }

    @Override
    public float getBulletVelocity() {
        return 25;
    }

    @Override
    public float getDamage() {
        return 35;
    }

    @Override
    public int cooldownTime() {
        return 100;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.SNIPE_GUN.get();
    }
}
