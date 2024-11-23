package com.arc.outland_horizon.world.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer instanceof ServerPlayer player && pUsedHand.equals(InteractionHand.MAIN_HAND)) {
            if (canUse(pLevel, player, player.getMainHandItem())) {
                startCooldown(player.getItemInHand(pUsedHand));
                successfullyUsed(pLevel, player, pUsedHand);
                pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(pUsedHand));
            } else {
                unsuccessfullyUsed(pLevel, player, pUsedHand);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void unsuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
    }

    public abstract void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand);
}
