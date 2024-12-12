package com.arc.outland_horizon.world.block.fluids.blood;

import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class ArterialBlood extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES
            = new ForgeFlowingFluid.Properties(OHBlocks.Fluids.OHFluidTypes.ArterialBLOOD_TYPE,
            OHBlocks.Fluids.OHFluids.ArterialBLOOD, OHBlocks.Fluids.OHFluids.ArterialBLOOD_FLOWING)
            .explosionResistance(100f)
            .bucket(OHItems.Tool.ARTERIAL_BLOOD_BUCKET)
            .block(() -> (LiquidBlock) OHBlocks.Fluids.FluidBlocks.Arterial_BLOOD_BLOCK.get());

    protected ArterialBlood() {
        super(PROPERTIES);
    }

    public static class Flowing extends ArterialBlood {
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        public boolean isSource(@NotNull FluidState pState) {
            return false;
        }
    }

    public static class Source extends ArterialBlood {
        public int getAmount(@NotNull FluidState pState) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState pState) {
            return true;
        }
    }
}
