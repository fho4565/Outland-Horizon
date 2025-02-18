package com.arc.outland_horizon.core;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

public enum Direction4 {
    NORTH(Direction.NORTH.getStepX(), Direction.NORTH.getStepZ()),
    EAST(Direction.EAST.getStepX(), Direction.EAST.getStepZ()),
    SOUTH(Direction.SOUTH.getStepX(), Direction.SOUTH.getStepZ()),
    WEST(Direction.WEST.getStepX(), Direction.WEST.getStepZ());

    private final Vec3i step;

    Direction4(int x, int z) {
        this.step = new Vec3i(x, 0, z);


    }


    public int getStepX() {
        return this.step.getX();
    }

    public int getStepZ() {
        return this.step.getZ();
    }
}
