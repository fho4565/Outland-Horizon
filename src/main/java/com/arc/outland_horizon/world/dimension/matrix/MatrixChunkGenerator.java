package com.arc.outland_horizon.world.dimension.matrix;

import com.arc.outland_horizon.registry.OHBiomes;
import com.arc.outland_horizon.world.dimension.matrix.rooms.EmptyRoom;
import com.arc.outland_horizon.world.dimension.matrix.rooms.RewardRoom;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixChunkGenerator extends ChunkGenerator {
    public static final Codec<MatrixChunkGenerator> CODEC = RecordCodecBuilder.create((p_255576_) -> {
        return p_255576_.group(RegistryOps.retrieveElement(OHBiomes.MATRIX)).apply(p_255576_, p_255576_.stable(MatrixChunkGenerator::new));
    });
    private final long seed;


    public MatrixChunkGenerator(Holder.Reference<Biome> biomeReference) {
        super(new FixedBiomeSource(biomeReference));
        seed = ThreadLocalRandom.current().nextLong();
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void createStructures(RegistryAccess pRegistryAccess, ChunkGeneratorStructureState pStructureState, StructureManager pStructureManager, ChunkAccess pChunk, StructureTemplateManager pStructureTemplateManager) {

    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel pLevel, ChunkAccess pChunk, StructureManager pStructureManager) {

    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, RandomState pRandom, BiomeManager pBiomeManager, StructureManager pStructureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {

    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager pStructureManager, RandomState pRandom, ChunkAccess chunk) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {

    }

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        long chunkSeed = seed + (long) chunkX * chunkZ;
        boolean next = chunkSeed % 2 == 0;
        if (next) {
            new EmptyRoom().place(chunk, BlockPos.containing(chunkX, 0, chunkZ));
        } else {
            new RewardRoom().place(chunk, BlockPos.containing(chunkX, 0, chunkZ));
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<Biome> pBiome, StructureManager pStructureManager, MobCategory pCategory, BlockPos pPos) {
        return WeightedRandomList.create();
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Heightmap.Types pType, LevelHeightAccessor pLevel, RandomState pRandom) {
        return 16;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> infos, RandomState randomState, BlockPos blockPos) {
        int chunkX = blockPos.getX() / 16;
        int chunkZ = blockPos.getZ() / 16;
        long chunkSeed = seed + (long) chunkX * chunkZ;
        boolean next = chunkSeed % 2 == 0;
        if (next) {
            infos.add("Current room : empty");
        } else {
            infos.add("Current room : reward");
        }
    }
}
