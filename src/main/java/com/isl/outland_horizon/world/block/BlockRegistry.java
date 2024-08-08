package com.isl.outland_horizon.world.block;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.block.fluids.blood.*;
import com.isl.outland_horizon.world.block.logs.NightmareLog;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {
    public static List<RegistryObject<Block>> BLOCK_LIST = new ArrayList<>();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final RegistryObject<Block> NIGHTMARE_LOG = register("nightmare_log", NightmareLog::new);
    public static final RegistryObject<Block> BLOOD_BLOCK = register("blood", BloodBlock::new);
    public static final RegistryObject<Block> Arterial_BLOOD_BLOCK = register("arterial_blood", ArterialBloodBlock::new);
    public static final RegistryObject<Block> NIGHTMARE_STONE = register("nightmare_stone", () ->
            new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> NIGHTMARE_DIRT = register("nightmare_dirt",NightmareDirt::new);
    public static final RegistryObject<Block> FLESH_BLOCK = register("flesh_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .sound(new ForgeSoundType(0.5f,1,
                    ()-> SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_broken"))
                    , SoundType.MUD::getStepSound
                    , SoundType.MUD::getPlaceSound
                    ,()-> SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_breaking"))
                    , SoundType.MUD::getFallSound
                    ))
            .requiresCorrectToolForDrops()
            .strength(2.8F, 12.0F)));
    public static final RegistryObject<Block> SCARRED_FLESH_BLOCK = register("scarred_flesh_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .sound(new ForgeSoundType(0.5f,1,
                    ()-> SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_broken"))
                    , SoundType.MUD::getStepSound
                    , SoundType.MUD::getPlaceSound
                    ,()-> SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_breaking"))
                    , SoundType.MUD::getFallSound
            ))
            .requiresCorrectToolForDrops()
            .strength(2.4F, 11.0F)));

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
        public static final RegistryObject<FluidType> ArterialBLOOD_TYPE = FLUID_TYPES.register("arterial_blood_type", ArterialBloodType::new);
    }
    public static class FluidRegistry{
        public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Utils.MOD_ID);
        public static final RegistryObject<FlowingFluid> BLOOD = FLUIDS.register("blood", Blood.Source::new);
        public static final RegistryObject<FlowingFluid> BLOOD_FLOWING = FLUIDS.register("blood_flowing", Blood.Flowing::new);

        public static final RegistryObject<FlowingFluid> ArterialBLOOD = FLUIDS.register("arterial_blood", ArterialBlood.Source::new);
        public static final RegistryObject<FlowingFluid> ArterialBLOOD_FLOWING = FLUIDS.register("arterial_blood_flowing", ArterialBlood.Flowing::new);
    }
}
