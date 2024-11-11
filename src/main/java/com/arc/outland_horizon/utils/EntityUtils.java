package com.arc.outland_horizon.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityUtils {
    public static void hurt(Entity source, Entity target, ResourceKey<DamageType> damageType, float damage) {
        target.hurt(new DamageSource(source.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), source), damage);
    }

    public static void hurt(Entity target, ResourceKey<DamageType> damageType, float damage) {
        target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType)), damage);
    }

    public static boolean isInDimension(Entity entity, ResourceLocation dimensionLocation) {
        return entity.level().dimension().location().compareTo(dimensionLocation) == 0;
    }

    public static @NotNull ResourceKey<DamageType> getMachineGun(LivingEntity holder, ResourceLocation location) {
        return holder.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, location)).key();
    }

    public static boolean isMoving(LivingEntity entity){
        return entity.getDeltaMovement().lengthSqr() > 0.015;
    }
    public static boolean isAttacking(LivingEntity entity){
        return entity.getAttackAnim(1.0F) > 0.0F;
    }
}
