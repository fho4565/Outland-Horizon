package com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.isl.outland_horizon.utils.EntityUtils;
import com.isl.outland_horizon.utils.WorldUtils;
import com.isl.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.AbstractRangedWeapon;
import com.isl.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Genocide extends AbstractRangedWeapon {
    public Genocide(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }
    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public float getDamage() {
        return 30;
    }

    @Override
    public void onPojectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        List<Entity> entities = target.level().getEntities(target,
                new AABB(target.getX() - 10, target.getY() - 5, target.getZ() - 10,
                        target.getX() + 10, target.getY() + 5, target.getZ() + 10));
        WorldUtils.playSound(target.level(), target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS);

        entities.stream().filter(entity -> entity instanceof LivingEntity)
                .forEach(entity -> EntityUtils.hurt(shooter, entity, DamageTypes.MOB_ATTACK, getDamage()));
        super.onPojectileHitEntity(projectile, target, shooter);
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(pLevel, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEventRegister.ROCKET_LAUNCHER.get(), SoundSource.PLAYERS);
        Bullet bullet = new Bullet(serverPlayer, this, 180, 8);
        bullet.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        pLevel.addParticle(ParticleTypes.FLAME,
                serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z,
                0.1, 0.1, 0.1);
        bullet.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, 15, 0);
        pLevel.addFreshEntity(bullet);
    }
}
