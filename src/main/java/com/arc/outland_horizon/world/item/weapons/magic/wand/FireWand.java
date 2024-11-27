package com.arc.outland_horizon.world.item.weapons.magic.wand;

import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.entity.projectile.magic.FireWandShot;
import com.arc.outland_horizon.world.item.weapons.IRangedWeapon;
import com.arc.outland_horizon.world.item.weapons.magic.MagicWeapon;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class FireWand extends MagicWeapon {
    public FireWand() {
        super(150, 15, Ingredient.EMPTY, new Properties());
    }

    @Override
    public float getDamage() {
        return 6;
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.STAFF_SHOOT.get();
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getUseSoundEvent(), SoundSource.PLAYERS);
        BasePlayerProjectile wandShot = getProjectile(serverPlayer, this, 60, 3);
        wandShot.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        wandShot.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, getBulletVelocity(), getBulletInaccuracy());
        pLevel.addFreshEntity(wandShot);
    }

    @Override
    public BasePlayerProjectile getProjectile(LivingEntity shooter, IRangedWeapon weapon, int maxAge, float velocity) {
        return new FireWandShot(shooter, weapon, maxAge, velocity);
    }

    @Override
    public int getBulletMaxAge() {
        return 60;
    }

    @Override
    public float getBulletVelocity() {
        return 3.0f;
    }

    @Override
    public float getBulletInaccuracy() {
        return 0.0f;
    }

    @Override
    public int cooldownTime() {
        return 20;
    }
}
