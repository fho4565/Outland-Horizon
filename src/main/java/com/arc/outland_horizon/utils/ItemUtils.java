package com.arc.outland_horizon.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ItemUtils {
    /**
     * 扣除玩家hand部位的物品amount耐久度
     *
     * @param serverPlayer 服务器玩家
     * @param hand         主手或者副手
     * @param amount       扣除的耐久度，大于当前耐久度则物品损坏
     */
    public static void damageItemStack(ServerPlayer serverPlayer, InteractionHand hand, int amount) {
        serverPlayer.getItemInHand(hand).hurtAndBreak(--amount, serverPlayer, (plr) -> plr.broadcastBreakEvent(hand));
    }

    public static void damageItemStack(ServerPlayer serverPlayer, ItemStack itemStack, int amount) {
        InteractionHand hand = inWhichHand(serverPlayer, itemStack);
        itemStack.hurtAndBreak(--amount, serverPlayer, plr -> plr.broadcastBreakEvent(hand));
    }

    public static InteractionHand inWhichHand(ServerPlayer player, ItemStack itemStack) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(itemStack.getItem()) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    /**
     * 扣除玩家hand部位的物品amount耐久度
     *
     * @param serverPlayer 服务器玩家
     * @param hand         主手或者副手
     * @param amount       扣除的耐久度，大于当前耐久度则物品损坏
     * @param onBreak      当物品损坏时执行的代码
     */
    public static void damageItemStack(ServerPlayer serverPlayer, InteractionHand hand, int amount, Consumer<ServerPlayer> onBreak) {
        serverPlayer.getItemInHand(hand).hurtAndBreak(--amount, serverPlayer, onBreak);
    }
}
