package com.isl.outland_horizon.world.item.weapons.weapon.magic.wand;

import com.isl.outland_horizon.utils.EntityUtils;
import com.isl.outland_horizon.world.entity.projectile.magic.FireWandShot;
import com.isl.outland_horizon.world.item.weapons.weapon.magic.AbstractMagicWeapon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
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
    public void onPojectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        EntityUtils.hurt(shooter, target, DamageTypes.MAGIC, getDamage());
        super.onPojectileHitEntity(projectile, target, shooter);
    }

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public void doProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter) {
        super.doProjectileHitBlock(projectile, location, shooter);
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
        pLevel.addFreshEntity(new FireWandShot(serverPlayer, this, 60,3));
    }
}
