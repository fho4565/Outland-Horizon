package com.isl.outland_horizon.world.entity.projectile;

import com.isl.outland_horizon.world.entity.EntityRegistry;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class FireWandShot extends BasePlayerProjectile{
    public FireWandShot(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public FireWandShot(Level world) {
        super(EntityRegistry.FIREWANDSHOT.get(), world);
    }

    public FireWandShot(LivingEntity shooter, AbstractWeapon weapon, int maxAge) {
        super(EntityRegistry.FIREWANDSHOT.get(), shooter, weapon, maxAge);
    }

    public FireWandShot(Level world, double x, double y, double z) {
        super(EntityRegistry.FIREWANDSHOT.get(), world, x, y, z);
    }
}
