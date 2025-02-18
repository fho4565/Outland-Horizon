package com.arc.outland_horizon.world.dimension.matrix.rooms;

import com.fho4565.brick_lib.tools.placer.Placer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;

public abstract class RoomBase {
    protected Placer placer = new Placer();

    protected void placeOutline() {
        placer.savepoint();
        placer.savepoint();
        for (int i = 0; i < 15; i++) {
            placer.placeBlock(Blocks.QUARTZ_BLOCK.defaultBlockState());
            placer.savepoint();
            placer.moveAndPlaceBlocks(Placer.MoveDirection.FORMER, Blocks.QUARTZ_BLOCK.defaultBlockState(), 15);
            placer.rollback();
            placer.move(Placer.MoveDirection.RIGHT, 1);
        }
        placer.rollback();
        for (int i = 0; i < 16; i++) {
            placer.placeBlock(Blocks.QUARTZ_BLOCK.defaultBlockState());
            placer.moveAndPlaceBlocks(Placer.MoveDirection.FORMER, Blocks.QUARTZ_BLOCK.defaultBlockState(), 15);
            placer.moveAndPlaceBlocks(Placer.MoveDirection.RIGHT, Blocks.QUARTZ_BLOCK.defaultBlockState(), 15);
            placer.moveAndPlaceBlocks(Placer.MoveDirection.BACK, Blocks.QUARTZ_BLOCK.defaultBlockState(), 15);
            placer.moveAndPlaceBlocks(Placer.MoveDirection.LEFT, Blocks.QUARTZ_BLOCK.defaultBlockState(), 15);
            placer.move(Placer.MoveDirection.UP, 1);
        }
        placer.rollback();
        placeAddition(this.placer);
    }

    protected abstract void placeAddition(Placer placer);

    public void place(LevelAccessor levelAccessor, BlockPos origin) {
        placeOutline();
        placer.place(levelAccessor, origin);
    }

    public void place(ChunkAccess chunkAccess, BlockPos origin) {
        placeOutline();
        placer.place(chunkAccess, origin);
    }
}
