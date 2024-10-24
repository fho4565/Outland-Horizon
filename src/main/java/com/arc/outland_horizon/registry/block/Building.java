package com.arc.outland_horizon.registry.block;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.block.logs.NightmareLog;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.RegistryObject;

public class Building {
    public static final RegistryObject<Block> NIGHTMARE_LOG = BlockRegistry.register("nightmare_log", NightmareLog::new);
    public static final RegistryObject<Block> NIGHTMARE_STONE = BlockRegistry.register("nightmare_stone", () ->
            new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 6.0F)));
    public static final RegistryObject<Block> FLESH_BLOCK = BlockRegistry.register("flesh_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .sound(new ForgeSoundType(0.5f,1
                    , SoundType.MUD::getBreakSound
                    , SoundType.MUD::getStepSound
                    , SoundType.MUD::getPlaceSound
                    ,()-> SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("flesh_block_breaking"))
                    , SoundType.MUD::getFallSound
                    ))
            .requiresCorrectToolForDrops()
            .strength(3.5F, 12.0F)));
    public static final RegistryObject<Block> SCARRED_FLESH_BLOCK = BlockRegistry.register("scarred_flesh_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .sound(new ForgeSoundType(0.5f,1
                    ,SoundType.MUD::getBreakSound
                    , SoundType.MUD::getStepSound
                    , SoundType.MUD::getPlaceSound
                    ,()-> SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("flesh_block_breaking"))
                    , SoundType.MUD::getFallSound
            ))
            .requiresCorrectToolForDrops()
            .strength(3.2F, 11.0F)));
    public static class DUNGEON{
        public static final RegistryObject<Block> DUNGEON_BRICK = BlockRegistry.register("dungeon_brick", () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .sound(new ForgeSoundType(0.5f,1,
                        SoundType.NETHER_BRICKS::getBreakSound
                        , SoundType.NETHER_BRICKS::getStepSound
                        , SoundType.NETHER_BRICKS::getPlaceSound
                        ,SoundType.NETHER_BRICKS::getHitSound
                        , SoundType.NETHER_BRICKS::getFallSound
                ))
                .requiresCorrectToolForDrops()
                .strength(15F, 6000.0F)));
        public static void init(){}
    }
    public static void init(){
        DUNGEON.init();
    }
}
