package com.arc.outland_horizon.world.entity.projectile.magic;

import com.arc.outland_horizon.registry.OHEntities;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.item.weapons.IRangedWeapon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class FireWandShot extends BasePlayerProjectile {
    public FireWandShot(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    public FireWandShot(Level world) {
        super(OHEntities.FIREWAND_SHOT.get(), world);
    }

    public FireWandShot(LivingEntity shooter, IRangedWeapon weapon, float maxAge, float velocity) {
        super(OHEntities.FIREWAND_SHOT.get(), shooter, weapon, maxAge, velocity, 1);
    }

    public FireWandShot(Level world, double x, double y, double z, int velocity) {
        super(OHEntities.FIREWAND_SHOT.get(), world, x, y, z, velocity);
    }
}
