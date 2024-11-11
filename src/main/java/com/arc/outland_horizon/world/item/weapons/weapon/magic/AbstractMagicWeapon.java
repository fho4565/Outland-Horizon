package com.arc.outland_horizon.world.item.weapons.weapon.magic;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractMagicWeapon extends AbstractWeapon {
    public AbstractMagicWeapon(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient,new Properties());
    }
    public AbstractMagicWeapon(int maxDurability, int enchantAbility, Item repairIngredient, Properties properties) {
        super(maxDurability, 1, enchantAbility, repairIngredient,properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(pPlayer instanceof ServerPlayer player && pUsedHand.equals(InteractionHand.MAIN_HAND)){
            if(CapabilityUtils.removeMana(player,getManaCost())){
                player.getCooldowns().addCooldown(this, getCoolDown());
                successfullyUsed(pLevel, player, pUsedHand);
            }else{
                unsuccessfullyUsed(pLevel, player, pUsedHand);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public abstract int getCoolDown();
    public abstract int getManaCost();
    public abstract SoundEvent getSoundEvent();
    public abstract BasePlayerProjectile getProjectile(LivingEntity shooter, AbstractWeapon weapon, int maxAge, int velocity);
    public abstract float getBulletMaxAge();
    public abstract float getBulletVelocity();
    public abstract float getBulletInaccuracy();
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand){
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getSoundEvent(), SoundSource.PLAYERS);
        BasePlayerProjectile wandShot = getProjectile(serverPlayer, this, 60, 3);
        wandShot.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        wandShot.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, getBulletVelocity(), getBulletInaccuracy());
        pLevel.addFreshEntity(wandShot);
    }
    public void unsuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand){
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag pIsAdvanced) {
        components.add(Component.empty());
        components.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.magic_damage",getDamage()).withStyle(ChatFormatting.DARK_GREEN));
        components.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.magic_cost",getManaCost()).withStyle(ChatFormatting.DARK_GREEN));
        components.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.reload_cooldown",getCoolDown()).withStyle(ChatFormatting.DARK_GREEN));
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
    }

}
