package com.isl.outland_horizon.world.block.fluids.blood;

import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class ArterialBlood extends ForgeFlowingFluid {
    public static final Properties PROPERTIES
            = new Properties(BlockRegistry.FluidTypeRegistry.ArterialBLOOD_TYPE,
            BlockRegistry.FluidRegistry.ArterialBLOOD, BlockRegistry.FluidRegistry.ArterialBLOOD_FLOWING)
            .explosionResistance(100f)
            .block(() -> (LiquidBlock) BlockRegistry.Arterial_BLOOD_BLOCK.get());

    protected ArterialBlood() {
        super(PROPERTIES);
    }

    public static class Flowing extends ArterialBlood {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        public boolean isSource(FluidState pState) {
            return false;
        }
    }

    public static class Source extends ArterialBlood {
        public int getAmount(FluidState pState) {
            return 8;
        }

        public boolean isSource(FluidState pState) {
            return true;
        }
    }
}
