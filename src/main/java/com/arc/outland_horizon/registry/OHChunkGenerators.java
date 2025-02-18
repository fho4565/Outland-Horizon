package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.world.dimension.matrix.MatrixChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OHChunkGenerators {
    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, "outland_horizon");
    public static final RegistryObject<Codec<? extends ChunkGenerator>> MATRIX = CHUNK_GENERATORS.register("matrix", () -> MatrixChunkGenerator.CODEC);
}
