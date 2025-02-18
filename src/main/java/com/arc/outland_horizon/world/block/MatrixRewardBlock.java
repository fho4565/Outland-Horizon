package com.arc.outland_horizon.world.block;

import com.arc.outland_horizon.registry.OHData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MatrixRewardBlock extends Block {
    public static final BooleanProperty OBTAINED = BooleanProperty.create("obtained");

    public MatrixRewardBlock() {
        super(Properties.copy(Blocks.BEDROCK));
        this.registerDefaultState(this.stateDefinition.any().setValue(OBTAINED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        Boolean value = state.getValue(OBTAINED);
        if (!value) {
            if (level instanceof ServerLevel serverLevel) {
                Vec3 center = blockPos.above().getCenter();
                LootParams params = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.ORIGIN, center).create(LootContextParamSets.GIFT);
                level.getServer().getLootData().getLootTable(OHData.LootTables.MATRIX_REWARD).getRandomItems(params).forEach(itemStack -> level.addFreshEntity(new ItemEntity(serverLevel, center.x(), center.y(), center.z(), itemStack)));
                serverLevel.setBlock(blockPos, state.setValue(OBTAINED, true), 3);
                return super.use(state, level, blockPos, player, pHand, pHit);
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OBTAINED);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(OBTAINED, false);
    }
}
