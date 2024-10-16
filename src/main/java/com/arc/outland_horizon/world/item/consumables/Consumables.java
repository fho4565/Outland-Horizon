package com.arc.outland_horizon.world.item.consumables;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Consumables extends Item {

    public Consumables(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand interactionHand) {
        if (interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.startUsingItem(interactionHand);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
