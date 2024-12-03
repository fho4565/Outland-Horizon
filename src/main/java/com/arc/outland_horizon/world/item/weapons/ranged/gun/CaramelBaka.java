package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.item.DeveloperItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CaramelBaka extends Gun implements DeveloperItem {
    public CaramelBaka() {
        super(2160, 20, Ingredient.of(OHItems.Material.REINFORCED_GOLD_INGOT.get()), new Properties().rarity(Rarity.create("caramel", style -> style.withColor(ChatFormatting.YELLOW).withBold(true))));
    }

    @Override
    public float getDamage() {
        return 27;
    }

    @Override
    public void onProjectileHitEntity(Projectile projectile, Entity target, LivingEntity shooter) {
        WorldUtils.getEntitiesByRadio(target.level(), target.position(), 5).forEach(entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.is(target)) {
                    EntityUtils.hurt(shooter, livingEntity, DamageTypes.MOB_ATTACK, getDamage() * 0.65f);
                }
            }
        });
        super.onProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public int getBulletMaxAge() {
        return 30;
    }

    @Override
    public float getBulletVelocity() {
        return 8;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(developerNameTooltip());
        super.appendHoverText(itemStack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public ResourceKey<DamageType> getDamageType(LivingEntity holder) {
        return EntityUtils.getMachineGun(holder, OutlandHorizon.createModResourceLocation("machine_gun"));
    }

    @Override
    public String developerName() {
        return "caramel";
    }

    @Override
    public int cooldownTime() {
        return 8;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.GUN.get();
    }
}
