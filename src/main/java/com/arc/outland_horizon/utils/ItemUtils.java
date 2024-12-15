package com.arc.outland_horizon.utils;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ItemUtils {
    /**
     * 扣除玩家hand部位的物品amount耐久度
     *
     * @param player 服务器玩家
     * @param hand   主手或者副手
     * @param amount 扣除的耐久度，大于当前耐久度则物品损坏
     */
    public static void damageItemStack(Player player, InteractionHand hand, int amount) {
        if (player.isCreative()) {
            return;
        }
        player.getItemInHand(hand).hurtAndBreak(--amount, player, (plr) -> plr.broadcastBreakEvent(hand));
    }

    public static void damageItemStack(Player player, ItemStack itemStack, int amount) {
        if (player.isCreative()) {
            return;
        }
        InteractionHand hand = inWhichHand(player, itemStack);
        itemStack.hurtAndBreak(--amount, player, plr -> plr.broadcastBreakEvent(hand));
    }

    public static InteractionHand inWhichHand(Player player, ItemStack itemStack) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(itemStack.getItem()) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    /**
     * 扣除玩家hand部位的物品amount耐久度，对创造模式玩家无效
     *
     * @param player  服务器玩家
     * @param hand    主手或者副手
     * @param amount  扣除的耐久度，大于当前耐久度则物品损坏
     * @param onBreak 当物品损坏时执行的代码
     */
    public static void damageItemStack(Player player, InteractionHand hand, int amount, Consumer<Player> onBreak) {
        if (player.isCreative()) {
            return;
        }
        player.getItemInHand(hand).hurtAndBreak(--amount, player, onBreak);
    }
}
