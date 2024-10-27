package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class Malicious extends Gun {
    public Malicious(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability,  enchantAbility, repairIngredient);
    }

    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public int getBulletMaxAge() {
        return 16;
    }

    @Override
    public int getBulletVelocity() {
        return 5;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.GUN.get();
    }

    @Override
    public float getDamage() {
        return 3;
    }
}
