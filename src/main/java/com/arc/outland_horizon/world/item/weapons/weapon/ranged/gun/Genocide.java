package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

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
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;

public class Genocide extends Gun {
    public Genocide(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability,  enchantAbility, repairIngredient);
    }
    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public int getBulletMaxAge() {
        return 6;
    }

    @Override
    public int getBulletVelocity() {
        return 8;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.ROCKET_LAUNCHER.get();
    }

    @Override
    public float getDamage() {
        return 30;
    }

    @Override
    public void onProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        WorldUtils.playSound(target.level(), target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS,2,1);
        EntityUtils.getEntitiesByRadio(projectile.level(), projectile.position(), 5)
                .stream().filter(entity -> entity instanceof LivingEntity && !entity.is(target))
                .forEach(entity -> EntityUtils.hurt(shooter, entity, DamageTypes.MOB_ATTACK, (float) (getDamage()+shooter.getAttributeValue(Attributes.ATTACK_DAMAGE))/2.0f));
        super.onProjectileHitEntity(projectile, target, shooter);
    }
}
