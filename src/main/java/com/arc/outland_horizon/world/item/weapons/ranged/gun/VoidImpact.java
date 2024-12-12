package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class VoidImpact extends Gun {
    public VoidImpact() {
        super(350, 10, Ingredient.of(OHItems.Material.VOID_CRYSTAL.get()), new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public int getBulletMaxAge() {
        return 5;
    }

    @Override
    public float getBulletVelocity() {
        return 10;
    }

    @Override
    public float getBulletInaccuracy() {
        return 2;
    }

    @Override
    public float getDamage() {
        return 7;
    }


    @Override
    public ResourceKey<DamageType> getDamageType(LivingEntity holder) {
        return EntityUtils.getDamageType(holder, OutlandHorizon.createModResourceLocation("machine_gun"));
    }

    @Override
    public int cooldownTime() {
        return 5;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.MACHINE_GUN.get();
    }
}
