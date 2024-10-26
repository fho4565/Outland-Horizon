package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class FrequencyVariation extends Gun {
    public FrequencyVariation(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, enchantAbility, repairIngredient);
    }

    @Override
    public int getCoolDown() {
        return 100;
    }

    @Override
    public int getBulletMaxAge() {
        return 10;
    }

    @Override
    public int getBulletVelocity() {
        return 14;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.SNIPE_GUN.get();
    }

    @Override
    public float getDamage() {
        return 35;
    }
}
