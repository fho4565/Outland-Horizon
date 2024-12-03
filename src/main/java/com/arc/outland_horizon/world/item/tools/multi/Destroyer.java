package com.arc.outland_horizon.world.item.tools.multi;

import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class Destroyer extends DiggerItem {
    public Destroyer(Tier pTier, Properties pProperties) {
        super(1, -3.2f, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack pStack, @Nonnull Level pLevel, @Nonnull BlockState pState, @Nonnull BlockPos pos, @Nonnull LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof Player player) {
            if (isCorrectToolForDrops(pStack, pState)) {
                doBreakBlocks(pLevel, pos.getX(), pos.getY(), pos.getZ(), player, pStack, pState);
            }
        }
        return super.mineBlock(pStack, pLevel, pState, pos, pEntityLiving);
    }

    public void doBreakBlocks(Level world, double x, double y, double z, Player player, ItemStack stack, BlockState pState) {
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
                        if (checkBlock(world.getBlockState(BlockPos.containing(x + i, y, z + j)), pState)) {
                            {
                                BlockPos blockPos = BlockPos.containing(x + i, y, z + j);
                                stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                                world.destroyBlock(blockPos, true);
                            }
                        }
                    } else if ((player.getDirection()).getAxis() == Direction.Axis.Z) {
                        if (checkBlock(world.getBlockState(BlockPos.containing(x + i, y + j, z)), pState)) {
                            {
                                BlockPos blockPos = BlockPos.containing(x + i, y + j, z);
                                stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                                world.destroyBlock(blockPos, true);
                            }
                        }
                    } else if ((player.getDirection()).getAxis() == Direction.Axis.X) {
                        if (checkBlock(world.getBlockState(BlockPos.containing(x, y + j, z + i)), pState)) {
                            {
                                BlockPos blockPos = BlockPos.containing(x, y + j, z + i);
                                stack.hurtAndBreak(1, player, (serverPlayer) -> serverPlayer.broadcastBreakEvent(player.getUsedItemHand()));
                                world.destroyBlock(blockPos, true);
                            }
                        }
                    }
                }
                j++;
            }
            i++;
        }
    }

    @Override
    public boolean isCorrectToolForDrops(@Nonnull ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return net.minecraftforge.common.TierSortingRegistry.isCorrectTierForDrops(getTier(), state);
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        WorldUtils.getEntitiesByRadio(pAttacker.level(), pTarget.position(), 5)
                .forEach(entity -> {
                    if (entity instanceof LivingEntity livingEntity) {
                        EntityUtils.hurt(pAttacker, livingEntity, DamageTypes.MOB_ATTACK, this.getAttackDamage() / 2);
                        double d0 = pAttacker.getX() - pTarget.getX();

                        double d1;
                        for (d1 = pAttacker.getZ() - pTarget.getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }
                        livingEntity.knockback(1.0F, d0, d1);
                    }
                });
        return super.hurtEnemy(itemStack, pTarget, pAttacker);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        return (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) ? this.speed : 1.0F;
    }

    private boolean checkBlock(BlockState state, BlockState original) {
        return (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL))
                && state.is(original.getBlock());
    }
}
