package com.isl.outland_horizon.world.block.fluids.blood;

import com.isl.outland_horizon.world.item.registry.Tools;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class Blood extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES
            = new ForgeFlowingFluid.Properties(com.isl.outland_horizon.world.block.Fluid.FluidTypeRegistry.BLOOD_TYPE,
            com.isl.outland_horizon.world.block.Fluid.FluidRegistry.BLOOD, com.isl.outland_horizon.world.block.Fluid.FluidRegistry.BLOOD_FLOWING)
            .explosionResistance(100f)
            .bucket(Tools.BLOOD_BUCKET)
            .block(() -> (LiquidBlock) com.isl.outland_horizon.world.block.Fluid.BLOOD_BLOCK.get());

    protected Blood() {
        super(PROPERTIES);
    }

    public static class Flowing extends Blood {
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

    public static class Source extends Blood {
        public int getAmount(@NotNull FluidState pState) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState pState) {
            return true;
        }
    }
}
