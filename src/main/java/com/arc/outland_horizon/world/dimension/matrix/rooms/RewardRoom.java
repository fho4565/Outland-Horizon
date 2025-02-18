package com.arc.outland_horizon.world.dimension.matrix.rooms;

import com.arc.outland_horizon.registry.OHBlocks;
import com.fho4565.brick_lib.tools.placer.Placer;

public class RewardRoom extends RoomBase {

    @Override
    protected void placeAddition(Placer placer) {
        System.out.println("reward room");
        placer.move(Placer.MoveDirection.FORMER, 8).move(Placer.MoveDirection.RIGHT, 8).move(Placer.MoveDirection.UP, 1);
        placer.placeBlock(OHBlocks.Functional.MATRIX_REWARD_BLOCK.get().defaultBlockState());
    }
}
