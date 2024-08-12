package com.isl.outland_horizon.world.item.weapons.weapon.ranged;

import com.isl.outland_horizon.utils.EntityUtils;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractRangedWeapon extends AbstractWeapon {
    public AbstractRangedWeapon(int maxDurability, int meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        super(maxDurability, meleeAttackDamage, enchantAbility, repairIngredient);
    }
    public ResourceKey<DamageType> getDamageType(LivingEntity holder){
        return DamageTypes.MOB_ATTACK;
    }
    public abstract int getCoolDown();

    @Override
    public void onPojectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        EntityUtils.hurt(shooter, target, getDamageType(shooter), getDamage());
        super.onPojectileHitEntity(projectile, target, shooter);
    }

    public abstract float getDamage();
    public abstract void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand);

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(pPlayer instanceof ServerPlayer player && pUsedHand.equals(InteractionHand.MAIN_HAND)){
            successfullyUsed(pLevel, player, pUsedHand);
            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(pUsedHand));
            player.getCooldowns().addCooldown(this, getCoolDown());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> components, TooltipFlag pIsAdvanced) {
        components.add(Component.literal("远程伤害："+getDamage()).withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
    }
}
