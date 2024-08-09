package com.isl.outland_horizon.world.block.fluids.blood;

import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class Blood extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES
            = new ForgeFlowingFluid.Properties(BlockRegistry.FluidTypeRegistry.BLOOD_TYPE,
            BlockRegistry.FluidRegistry.BLOOD, BlockRegistry.FluidRegistry.BLOOD_FLOWING)
            .explosionResistance(100f)
            .bucket(ItemRegistry.BLOOD_BUCKET)
            .block(() -> (LiquidBlock) BlockRegistry.BLOOD_BLOCK.get());

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
