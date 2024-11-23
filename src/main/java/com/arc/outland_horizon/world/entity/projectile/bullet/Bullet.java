package com.arc.outland_horizon.world.entity.projectile.bullet;

import com.arc.outland_horizon.world.entity.EntityRegistry;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.item.weapons.IOHRangedWeapon;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Bullet extends BasePlayerProjectile {
    public Bullet(EntityType<? extends ThrowableProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public Bullet(Level world) {
        super(EntityRegistry.IRON_BULLET.get(), world);
    }

    public Bullet(LivingEntity shooter, IOHRangedWeapon weapon, int maxAge, float velocity) {
        super(EntityRegistry.IRON_BULLET.get(), shooter, weapon, maxAge, velocity, 0);
    }

    public Bullet(Level world, double x, double y, double z, int velocity) {
        super(EntityRegistry.IRON_BULLET.get(), world, x, y, z, velocity);
    }

    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, pVelocity, pInaccuracy);
        Vec3 vec3 = pShooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, pShooter.onGround() ? 0.0D : vec3.y, vec3.z));
    }
}
