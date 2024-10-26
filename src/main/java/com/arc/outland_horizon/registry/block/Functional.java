package com.arc.outland_horizon.registry.block;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.registries.RegistryObject;

public class Functional {
    public static RegistryObject<Block> DUNGEON_TORCH = BlockRegistry.BLOCKS.register("dungeon_torch", () -> new TorchBlock(Block.Properties.copy(Blocks.TORCH), ParticleTypes.FLAME));
    public static RegistryObject<Block> WALL_DUNGEON_TORCH = BlockRegistry.BLOCKS.register("wall_dungeon_torch", () -> new WallTorchBlock(Block.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.FLAME));
    public static void init(){}
}
