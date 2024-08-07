package com.isl.outland_horizon.world.block.fluids.blood;

import com.isl.outland_horizon.world.block.BlockRegistry;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ArterialBloodBlock extends LiquidBlock {
    public ArterialBloodBlock() {
        super(BlockRegistry.FluidRegistry.ArterialBLOOD, Properties.of()
                .mapColor(MapColor.WATER)
                .strength(100f)
                .noCollission()
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.EMPTY)
                .replaceable()
        );
    }
}
