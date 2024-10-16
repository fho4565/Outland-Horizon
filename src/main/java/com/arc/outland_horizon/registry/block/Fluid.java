package com.arc.outland_horizon.registry.block;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.block.BlockRegistry;
import com.arc.outland_horizon.world.block.fluids.blood.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Fluid {
    public static final RegistryObject<Block> BLOOD_BLOCK = BlockRegistry.register("blood", BloodBlock::new);
    public static final RegistryObject<Block> Arterial_BLOOD_BLOCK = BlockRegistry.register("arterial_blood", ArterialBloodBlock::new);

    public static class FluidTypeRegistry{
        public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Utils.MOD_ID);
        public static final RegistryObject<FluidType> BLOOD_TYPE = FLUID_TYPES.register("blood_type", BloodType::new);
        public static final RegistryObject<FluidType> ArterialBLOOD_TYPE = FLUID_TYPES.register("arterial_blood_type", ArterialBloodType::new);
    }

    public static class FluidRegistry{
        public static final DeferredRegister<net.minecraft.world.level.material.Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Utils.MOD_ID);
        public static final RegistryObject<FlowingFluid> BLOOD = FLUIDS.register("blood", Blood.Source::new);
        public static final RegistryObject<FlowingFluid> BLOOD_FLOWING = FLUIDS.register("blood_flowing", Blood.Flowing::new);

        public static final RegistryObject<FlowingFluid> ArterialBLOOD = FLUIDS.register("arterial_blood", ArterialBlood.Source::new);
        public static final RegistryObject<FlowingFluid> ArterialBLOOD_FLOWING = FLUIDS.register("arterial_blood_flowing", ArterialBlood.Flowing::new);
    }
    public static void init(){}
}
