package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.arc.outland_horizon.world.item.UsableItem;
import com.arc.outland_horizon.world.item.weapons.IRangedWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Gun extends UsableItem implements IRangedWeapon {

    public Gun(int maxDurability, int enchantAbility, Ingredient repairIngredient, Properties properties) {
        super(maxDurability, enchantAbility, repairIngredient, properties);
    }

    @Override
    public BasePlayerProjectile getProjectile(LivingEntity shooter, IRangedWeapon weapon, int maxAge, float velocity) {
        return new Bullet(shooter, weapon, maxAge, velocity);
    }

    protected void fire(ServerPlayer serverPlayer) {
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getUseSoundEvent(), SoundSource.PLAYERS);
        BasePlayerProjectile bullet = getProjectile(serverPlayer, this, getBulletMaxAge(), getBulletVelocity());
        bullet.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        bullet.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, getBulletVelocity(), getBulletInaccuracy());
        serverPlayer.level().addFreshEntity(bullet);
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        fire(serverPlayer);

    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(itemStack, level, components, isAdvanced);
        components.add(rangedDamageTooltip());
        components.add(cooldownTooltip());
        components.add(inaccuracyTooltip());
    }
}
