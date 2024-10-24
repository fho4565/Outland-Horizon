package com.arc.outland_horizon.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.TriPredicate;

public class ModBlock extends Block {
    private TriPredicate<BlockGetter,BlockPos,Player> canHarvestBlock = (a,b,c) -> true;
    private TriPredicate<BlockGetter,BlockPos,Entity> canEntityDestroy = (a,b,c) -> true;

    public ModBlock(Properties pProperties) {
        super(pProperties);
    }
    public ModBlock(Properties pProperties, TriPredicate<BlockGetter, BlockPos, Player> canHarvestBlock) {
        super(pProperties);
        this.canHarvestBlock = canHarvestBlock;
    }
    public ModBlock(Properties pProperties, TriPredicate<BlockGetter, BlockPos, Entity> canEntityDestroy, TriPredicate<BlockGetter, BlockPos, Player> canHarvestBlock) {
        super(pProperties);
        this.canEntityDestroy = canEntityDestroy;
        this.canHarvestBlock = canHarvestBlock;
    }
    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return super.canHarvestBlock(state, level, pos, player) && canHarvestBlock.test(level,pos,player);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return super.canEntityDestroy(state, level, pos, entity) && canEntityDestroy.test(level,pos,entity);
    }
}
