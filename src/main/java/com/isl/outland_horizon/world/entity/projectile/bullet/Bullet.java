package com.isl.outland_horizon.world.entity.projectile.bullet;

import com.isl.outland_horizon.world.entity.EntityRegistry;
import com.isl.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class Bullet extends BasePlayerProjectile {
    public Bullet(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public Bullet(Level world) {
        super(EntityRegistry.IRON_BULLET.get(), world);
    }

    public Bullet(LivingEntity shooter, AbstractWeapon weapon, int maxAge, int velocity) {
        super(EntityRegistry.IRON_BULLET.get(), shooter, weapon, maxAge,velocity);
    }

    public Bullet(Level world, double x, double y, double z, int velocity) {
        super(EntityRegistry.IRON_BULLET.get(), world, x, y, z,velocity);
    }
}
