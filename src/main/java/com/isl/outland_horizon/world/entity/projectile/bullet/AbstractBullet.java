package com.isl.outland_horizon.world.entity.projectile.bullet;

import com.isl.outland_horizon.world.entity.EntityRegistry;
import com.isl.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class AbstractBullet extends BasePlayerProjectile {
    public AbstractBullet(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public AbstractBullet(Level world) {
        super(EntityRegistry.FIREWAND_SHOT.get(), world);
    }

    public AbstractBullet(EntityType<? extends ThrowableProjectile> entityType,LivingEntity shooter, AbstractWeapon weapon, int maxAge,int velocity) {
        super(entityType, shooter, weapon, maxAge,velocity);
    }

    public AbstractBullet(EntityType<? extends ThrowableProjectile> entityType,Level world, double x, double y, double z, int velocity) {
        super(entityType, world, x, y, z,velocity);
    }
}
