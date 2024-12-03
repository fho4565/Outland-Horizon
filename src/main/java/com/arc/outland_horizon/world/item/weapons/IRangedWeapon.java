package com.arc.outland_horizon.world.item.weapons;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public interface IRangedWeapon {
    default float getDamage() {
        return 1.0f;
    }

    BasePlayerProjectile getProjectile(LivingEntity shooter, IRangedWeapon weapon, int maxAge, float velocity);

    int getBulletMaxAge();

    float getBulletVelocity();

    default float getBulletInaccuracy() {
        return 0;
    }

    default void onProjectileHitBlock(Projectile projectile, Vec3 location, LivingEntity shooter) {
    }

    default ResourceKey<DamageType> getDamageType(LivingEntity holder) {
        return DamageTypes.MOB_ATTACK;
    }

    default void onProjectileHitEntity(Projectile projectile, Entity target, LivingEntity shooter) {
        EntityUtils.hurt(shooter, target, getDamageType(shooter), (float) (getDamage() + shooter.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1));
    }

    default Component rangedDamageTooltip() {
        return ChatUtils.translatable("text.outland_horizon.gui.weapon.ranged_damage", getDamage()).withStyle(ChatFormatting.DARK_GREEN);
    }

    default Component inaccuracyTooltip() {
        return ChatUtils.translatable("text.outland_horizon.gui.weapon.inaccuracy", getBulletInaccuracy()).withStyle(ChatFormatting.DARK_GREEN);
    }
}
