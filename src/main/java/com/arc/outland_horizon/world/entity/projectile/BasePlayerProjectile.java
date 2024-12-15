package com.arc.outland_horizon.world.entity.projectile;

import com.arc.outland_horizon.world.item.weapons.IRangedWeapon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class BasePlayerProjectile extends Projectile {
    public int age = 0;
    protected float lifespan = 30;
    protected IRangedWeapon weapon;
    float velocity = 6.0f;
    float inaccuracy = 0.0f;
    double lookX, lookY, lookZ;
    private Entity cachedOwner = null;

    protected BasePlayerProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BasePlayerProjectile(EntityType<? extends Projectile> entityType, LivingEntity shooter, IRangedWeapon weapon, float maxAge, float velocity, float inaccuracy) {
        super(entityType, shooter.level());
        this.lifespan = maxAge;
        this.weapon = weapon;
        this.velocity = velocity;
        this.inaccuracy = inaccuracy;
        this.lookX = shooter.getLookAngle().x;
        this.lookY = shooter.getLookAngle().y;
        this.lookZ = shooter.getLookAngle().z;
        setOwner(shooter);
        setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
        setRot(shooter.getYRot(), shooter.getXRot());
    }

    public BasePlayerProjectile(EntityType<? extends Projectile> entityType, Level world, double x, double y, double z, int velocity) {
        this(entityType, world);
        this.velocity = velocity;
        this.lookX = x;
        this.lookY = y;
        this.lookZ = z;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Entity getOwner() {
        if (this.cachedOwner != null && this.cachedOwner.isAlive()) {
            return this.cachedOwner;
        }

        this.cachedOwner = super.getOwner();

        return this.cachedOwner;
    }

    public void tick() {
        super.tick();
        Entity entity = this.getOwner();
        Optional<Entity> optionalEntity = Optional.ofNullable(this.getOwner());
        boolean flag = false;
        if (optionalEntity.isPresent()) {
            flag = optionalEntity.get().position().distanceTo(this.position()) <= 130;
        }
        if ((entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition()) && flag) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }
            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 1.0F);
            this.setPos(d0, d1, d2);
        } else {
            if (!this.level().isClientSide()) {
                this.discard();
            }
        }
        ++this.age;
        if (this.age >= this.lifespan) {
            if (!this.level().isClientSide()) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        if (!level().isClientSide) {
            if (weapon != null && isAlive()) {
                Entity shooter = getOwner();
                if (shooter instanceof LivingEntity) {
                    if (result.getType() == HitResult.Type.BLOCK) {
                        weapon.onProjectileHitBlock(this, result.getLocation(), (LivingEntity) shooter);
                    } else if (result.getType() == HitResult.Type.ENTITY) {
                        weapon.onProjectileHitEntity(this, ((EntityHitResult) result).getEntity(), (LivingEntity) shooter);
                    }
                }
            }
            discard();
        }
        super.onHit(result);
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}
