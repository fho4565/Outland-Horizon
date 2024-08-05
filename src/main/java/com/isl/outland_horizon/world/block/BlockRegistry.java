package com.isl.outland_horizon.world.block;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.block.fluids.Blood;
import com.isl.outland_horizon.world.block.fluids.BloodBlock;
import com.isl.outland_horizon.world.block.fluids.BloodType;
import com.isl.outland_horizon.world.block.logs.NightmareLog;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final RegistryObject<Block> NIGHTMARE_LOG = register("nightmare_log", NightmareLog::new);
    public static final RegistryObject<Block> BLOOD_BLOCK = register("blood", BloodBlock::new);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return register(id, block, true);
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem) {
        var object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.register(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
        return object;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
    public static class FluidTypeRegistry{
        public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Utils.MOD_ID);
        public static final RegistryObject<FluidType> BLOOD_TYPE = FLUID_TYPES.register("blood_type", BloodType::new);
    }
    public static class FluidRegistry{
        public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Utils.MOD_ID);
        public static final RegistryObject<FlowingFluid> BLOOD = FLUIDS.register("blood", Blood.Source::new);
        public static final RegistryObject<FlowingFluid> BLOOD_FLOWING = FLUIDS.register("blood_flowing", Blood.Flowing::new);

        @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class FluidsClientSideHandler {
            @SubscribeEvent
            public static void clientSetup(FMLClientSetupEvent event) {
                ItemBlockRenderTypes.setRenderLayer(BLOOD.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(BLOOD_FLOWING.get(), RenderType.translucent());
            }
        }
    }
}
