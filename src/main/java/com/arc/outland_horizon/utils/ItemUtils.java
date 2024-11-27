package com.arc.outland_horizon.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

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
        serverPlayer.getItemInHand(hand).hurtAndBreak(amount, serverPlayer, (plr) -> plr.broadcastBreakEvent(hand));
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
        serverPlayer.getItemInHand(hand).hurtAndBreak(amount, serverPlayer, onBreak);
    }
}
