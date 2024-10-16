package com.arc.outland_horizon.world.item.tools.multi;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Spade extends ShovelItem {
    public Spade(Tier pTier, Properties pProperties) {
        super(pTier, 1.0f, -3.2f, pProperties);
    }

    public static void doBreakBlocks(Level world, double x, double y, double z, Player player, ItemStack stack) {
        if (player == null)
            return;
        double i = -1, j;
        for (int m = 0; m < 3; m++) {
            j = -1;
            for (int n = 0; n < 3; n++) {
                if (i != 0 || j != 0) {
                    if (stack.isEmpty()) {
                        return;
                    }
                    if (player.getXRot() > 40 || player.getXRot() < -40) {
                        if ((world.getBlockState(BlockPos.containing(x + i, y, z + j))).is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                            BlockPos blockPos = BlockPos.containing(x + i, y, z + j);
                            stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                            world.destroyBlock(blockPos, true);

                        }
                    } else if ((player.getDirection()).getAxis() == Direction.Axis.Z) {
                        if ((world.getBlockState(BlockPos.containing(x + i, y + j, z))).is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                            BlockPos blockPos = BlockPos.containing(x + i, y + j, z);
                            stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                            world.destroyBlock(blockPos, true);

                        }
                    } else if ((player.getDirection()).getAxis() == Direction.Axis.X) {
                        if ((world.getBlockState(BlockPos.containing(x, y + j, z + i))).is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                            BlockPos blockPos = BlockPos.containing(x, y + j, z + i);
                            stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                            world.destroyBlock(blockPos, true);

                        }
                    }
                }
                j++;
            }
            i++;
        }
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pos, LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof Player player) {
            if (isCorrectToolForDrops(pStack, pState)) {
                doBreakBlocks(pLevel, pos.getX(), pos.getY(), pos.getZ(), player, pStack);
            }
        }
        return super.mineBlock(pStack, pLevel, pState, pos, pEntityLiving);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_SHOVEL) ? this.speed * 0.9f : 1.0F;
    }
}
