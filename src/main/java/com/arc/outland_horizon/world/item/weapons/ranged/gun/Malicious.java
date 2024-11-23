package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class Malicious extends Gun {
    public Malicious() {
        super(450, 10, Ingredient.of(OHItems.Material.NIGHTMARE_ENERGY.get()), new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getBulletMaxAge() {
        return 16;
    }

    @Override
    public float getBulletVelocity() {
        return 5;
    }


    @Override
    public float getDamage() {
        return 3;
    }

    @Override
    public int cooldownTime() {
        return 10;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.GUN.get();
    }
}
