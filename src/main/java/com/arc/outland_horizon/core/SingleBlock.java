package com.arc.outland_horizon.core;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;

public record SingleBlock(BlockPos blockPos, BlockState blockState) {
    public static SingleBlock of(BlockPos blockPos, BlockState blockState) {
        return new SingleBlock(blockPos, blockState);
    }

    public CompoundTag serialize() {
        CompoundTag compoundTag = new CompoundTag();
        long pos = blockPos.asLong();
        compoundTag.putLong("pos", pos);
        if (this.blockState != null) {
            Tag blockState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, this.blockState).get().orThrow();
            compoundTag.put("state", blockState);
        }
        return compoundTag;
    }

    public static SingleBlock deserialize(CompoundTag compoundTag) {
        long pos = compoundTag.getLong("pos");
        BlockState state = null;
        if (compoundTag.contains("state")) {
            state = BlockState.CODEC.decode(NbtOps.INSTANCE, compoundTag.get("state")).result().orElseThrow().getFirst();
        }
        return SingleBlock.of(BlockPos.of(pos), state);
    }
}

