package com.arc.outland_horizon.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityUtils {
    public static void hurt(Entity source, Entity target,ResourceKey<DamageType> damageType, float damage){
        target.hurt(new DamageSource(source.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), source), damage);
    }
    public static void hurt(Entity target, ResourceKey<DamageType> damageType, float damage){
        target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType)), damage);
    }
    public static List<Entity> getEntitiesByRadio(Level level, Vec3 pos, double radius){
        AABB box = new AABB(pos.x - radius-1, pos.y - radius-1, pos.z - radius-1,
                pos.x + radius+1, pos.y + radius+1, pos.z + radius+1);
        return level.getEntities(null,box).stream()
                .filter(entity -> entity.position().distanceTo(pos)<=radius).toList();
    }
    public static boolean isInDimension(Entity entity, ResourceLocation dimensionLocation){
        return entity.level().dimension().location().compareTo(dimensionLocation) == 0;
    }
}
