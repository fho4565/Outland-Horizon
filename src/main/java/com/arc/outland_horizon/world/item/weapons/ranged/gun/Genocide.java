package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

public class Genocide extends Gun {
    public Genocide() {
        super(100, 10, Ingredient.of(OHItems.Material.NIGHTMARE_ENERGY.get()), new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getBulletMaxAge() {
        return 6;
    }

    @Override
    public float getBulletVelocity() {
        return 5;
    }

    @Override
    public float getDamage() {
        return 30;
    }

    @Override
    public void onProjectileHitEntity(Projectile projectile, Entity target, LivingEntity shooter) {
        WorldUtils.playSound(target.level(), target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 2, 1);
        WorldUtils.getEntitiesByRadio(projectile.level(), projectile.position(), 5)
                .stream().filter(entity -> entity instanceof LivingEntity && !entity.is(target))
                .forEach(entity -> EntityUtils.hurt(shooter, entity, DamageTypes.MOB_ATTACK, (float) (getDamage() + shooter.getAttributeValue(Attributes.ATTACK_DAMAGE)) / 2.0f));
        super.onProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public int cooldownTime() {
        return 20;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.ROCKET_LAUNCHER.get();
    }
}
