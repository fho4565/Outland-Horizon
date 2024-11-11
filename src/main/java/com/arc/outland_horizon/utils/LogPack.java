package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.registry.block.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;


public class LogPack {
    private LogPack() {
    }

    public static void create(String name) {
        BlockRegistry.register(name + "_log", () -> getLog(MapColor.PODZOL));
        BlockRegistry.register(name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockRegistry.register("stripped"+name + "_log", () -> getLog(MapColor.WOOD));
        BlockRegistry.register("stripped"+name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockRegistry.register(name + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockRegistry.register(name + "_stairs", () -> new StairBlock(OAK_PLANKS::defaultBlockState, BlockBehaviour.Properties.copy(OAK_PLANKS)));
        BlockRegistry.register(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockRegistry.register(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(OAK_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockRegistry.register(name + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(OAK_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), WoodType.OAK));
        BlockRegistry.register(name + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(OAK_PLANKS.defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
        BlockRegistry.register(name + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava(), BlockSetType.OAK));
        BlockRegistry.register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(OAK_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
        BlockRegistry.register(name + "_button", () -> woodenButton(BlockSetType.OAK));
    }

    private static @NotNull RotatedPillarBlock getLog(MapColor color2) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD :color2).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }
    private static ButtonBlock woodenButton(BlockSetType pSetType) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);

        return new ButtonBlock(properties, pSetType, 30, true);
    }
}
