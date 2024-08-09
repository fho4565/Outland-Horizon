package com.isl.outland_horizon.world.entity.projectile.magic;

import com.isl.outland_horizon.world.entity.EntityRegistry;
import com.isl.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class FireWandShot extends BasePlayerProjectile {
    public FireWandShot(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public FireWandShot(Level world) {
        super(EntityRegistry.FIREWAND_SHOT.get(), world);
    }

    public FireWandShot(LivingEntity shooter, AbstractWeapon weapon, int maxAge,int velocity) {
        super(EntityRegistry.FIREWAND_SHOT.get(), shooter, weapon, maxAge,velocity);
    }

    public FireWandShot(Level world, double x, double y, double z, int velocity) {
        super(EntityRegistry.FIREWAND_SHOT.get(), world, x, y, z,velocity);
    }
}
