package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.AbstractRangedWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Gun extends AbstractRangedWeapon {
    public Gun(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }
    public Gun(int maxDurability, int enchantAbility, Item repairIngredient,Properties properties) {
        super(maxDurability, 1, enchantAbility, repairIngredient,properties);
    }

    @Override
    public BasePlayerProjectile getProjectile(LivingEntity shooter, AbstractWeapon weapon, int maxAge, int velocity) {
        return new Bullet(shooter, weapon, maxAge, velocity);
    }

    protected void fire(ServerPlayer serverPlayer){
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getSoundEvent(), SoundSource.PLAYERS);
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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
        components.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.reload_cooldown",getCoolDown()).withStyle(ChatFormatting.DARK_GREEN));
        components.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.inaccuracy",getBulletInaccuracy()).withStyle(ChatFormatting.DARK_GREEN));
    }
}
