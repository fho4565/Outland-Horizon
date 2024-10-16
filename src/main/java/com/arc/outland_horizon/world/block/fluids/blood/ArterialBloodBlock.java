package com.arc.outland_horizon.world.block.fluids.blood;

import com.arc.outland_horizon.registry.block.Fluid;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ArterialBloodBlock extends LiquidBlock {
    public ArterialBloodBlock() {
        super(Fluid.FluidRegistry.ArterialBLOOD, Properties.of()
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
