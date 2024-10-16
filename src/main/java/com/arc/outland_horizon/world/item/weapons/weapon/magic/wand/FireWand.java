package com.arc.outland_horizon.world.item.weapons.weapon.magic.wand;

import com.arc.outland_horizon.world.entity.projectile.magic.FireWandShot;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.AbstractMagicWeapon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireWand extends AbstractMagicWeapon{
    public FireWand() {
        super(300, 10, Items.DIAMOND);
    }


    @Override
    public void onProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        super.onProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public void onProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter) {
        super.onProjectileHitBlock(projectile, location, shooter);
    }

    @Override
    public int getCoolDown() {
        return 20;
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        FireWandShot wandShot = new FireWandShot(serverPlayer, this, 60, 3);
        wandShot.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0F, 3.0F, 0.0F);
        pLevel.addFreshEntity(wandShot);
    }
}
