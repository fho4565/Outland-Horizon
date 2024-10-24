package com.arc.outland_horizon.registry.block;

import com.arc.outland_horizon.world.block.NightmareDirt;
import com.arc.outland_horizon.world.block.Saplings;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Natural {
    public static final RegistryObject<Block> NIGHTMARE_DIRT = BlockRegistry.register("nightmare_dirt", NightmareDirt::new);
    public static final RegistryObject<Block> SAPLINGS = BlockRegistry.register("saplings", Saplings::new);
    public static final RegistryObject<Block> WHITE = BlockRegistry.register("pale_abyss_stone", ()->new Block(
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(12.0F, 24.0F)

    ));
    public static final RegistryObject<Block> CONDENSED_ORE = BlockRegistry.register("condensed_ore", ()->new Block(
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                    .strength(24.0F, 36.0F)
    ),true, Rarity.UNCOMMON);

    public static void init(){}
}
