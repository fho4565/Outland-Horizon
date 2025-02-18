package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class OHBiomes {
    public static final ResourceKey<Biome> MATRIX = ResourceKey.create(Registries.BIOME, OutlandHorizon.createModResourceLocation("matrix/matrix"));
}
