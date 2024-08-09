package com.isl.outland_horizon.world.item.consumables;

import com.isl.outland_horizon.utils.ManaUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
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
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 30;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack p_41409_, @NotNull Level p_41410_, @NotNull LivingEntity p_41411_) {
        if(p_41411_ instanceof ServerPlayer player){
            ManaUtils.addMana(player, 100);
        }
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }
}
