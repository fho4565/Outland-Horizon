package com.arc.outland_horizon.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DamagedDungeonBrick extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 1, 5);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    public DamagedDungeonBrick(Properties blockBehaviour) {
        super(blockBehaviour);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, 1));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = blockState.getValue(FACING);
        return switch (blockState.getValue(TYPE)) {
            case 2 -> createVoxelShape2(facing);
            case 3 -> createVoxelShape3(facing);
            case 4, 5 -> Shapes.block();
            default -> createDefaultVoxelShape(facing);
        };
    }

    private VoxelShape createVoxelShape2(Direction facing) {
        return switch (facing) {
            case NORTH ->
                    Shapes.or(box(7, 0, 0, 8, 16, 4), box(5, 0, 4, 8, 16, 8), box(8, 0, 0, 16, 16, 12), box(11, 0, 12, 16, 16, 16));
            case EAST ->
                    Shapes.or(box(12, 0, 7, 16, 16, 8), box(8, 0, 5, 12, 16, 8), box(4, 0, 8, 16, 16, 16), box(0, 0, 11, 4, 16, 16));
            case WEST ->
                    Shapes.or(box(0, 0, 8, 4, 16, 9), box(4, 0, 8, 8, 16, 11), box(0, 0, 0, 12, 16, 8), box(12, 0, 0, 16, 16, 5));
            default ->
                    Shapes.or(box(8, 0, 12, 9, 16, 16), box(8, 0, 8, 11, 16, 12), box(0, 0, 4, 8, 16, 16), box(0, 0, 0, 5, 16, 4));
        };
    }

    private VoxelShape createVoxelShape3(Direction facing) {
        return switch (facing) {
            case NORTH -> Shapes.or(box(11, 5, 12, 16, 16, 16), box(0, 0, 0, 16, 5, 16));
            case EAST -> Shapes.or(box(0, 5, 11, 4, 16, 16), box(0, 0, 0, 16, 5, 16));
            case WEST -> Shapes.or(box(12, 5, 0, 16, 16, 5), box(0, 0, 0, 16, 5, 16));
            default -> Shapes.or(box(0, 5, 0, 5, 16, 4), box(0, 0, 0, 16, 5, 16));
        };
    }

    private VoxelShape createDefaultVoxelShape(Direction facing) {
        return switch (facing) {
            case NORTH -> Shapes.or(box(0, 0, 0, 16, 13, 11), box(0, 0, 11, 16, 9, 16), box(0, 13, 0, 16, 16, 6));
            case EAST -> Shapes.or(box(5, 0, 0, 16, 13, 16), box(0, 0, 0, 5, 9, 16), box(10, 13, 0, 16, 16, 16));
            case WEST -> Shapes.or(box(0, 0, 0, 11, 13, 16), box(11, 0, 0, 16, 9, 16), box(0, 13, 0, 6, 16, 16));
            default -> Shapes.or(box(0, 0, 5, 16, 13, 16), box(0, 0, 0, 16, 9, 5), box(0, 13, 10, 16, 16, 16));
        };
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);

    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(TYPE, 1).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
    }


    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }


    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }


}
