package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.utils.ItemUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class UsableItem extends TieredItem implements ICooldownItem {
    public UsableItem(int uses, int enchantmentValue, Ingredient repairIngredient, Properties pProperties) {
        super(new Tier() {
            @Override
            public int getUses() {
                return uses;
            }

            @Override
            public float getSpeed() {
                return 8;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return enchantmentValue;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return repairIngredient;
            }
        }, pProperties);
    }

    public abstract SoundEvent getUseSoundEvent();

    public boolean canUse(Level pLevel, Player pPlayer, ItemStack itemStack) {
        return !isCooldown(itemStack);
    }

    @Override
    public void onUseTick(@Nonnull Level level, @Nonnull LivingEntity livingEntity, @Nonnull ItemStack itemStack, int remainingUseDuration) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (canUse(level, serverPlayer, itemStack)) {
                startCooldown(serverPlayer, itemStack);
                successfullyUsed(level, serverPlayer, itemStack);
                itemStack.hurtAndBreak(1, serverPlayer, serverPlayer1 -> serverPlayer1.broadcastBreakEvent(ItemUtils.inWhichHand(serverPlayer, itemStack)));
            } else {
                unsuccessfullyUsed(level, serverPlayer, InteractionHand.MAIN_HAND);
            }
        }
        super.onUseTick(level, livingEntity, itemStack, remainingUseDuration);
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack pStack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand interactionHand) {
        ItemStack item = player.getItemInHand(interactionHand);
        InteractionHand otherHand = interactionHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherItem = player.getItemInHand(otherHand);
        if (otherItem.canPerformAction(ToolActions.SHIELD_BLOCK) && !canUse(level, player, item)) {
            return InteractionResultHolder.fail(item);
        } else {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(item);
        }
    }

    public void unsuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
    }

    public abstract void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, ItemStack itemStack);
}
