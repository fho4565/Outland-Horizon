package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class OHDimensions {
    public static final ResourceKey<Level> NIGHTMARE = ResourceKey.create(Registries.DIMENSION, OutlandHorizon.createModResourceLocation("nightmare"));
    public static final ResourceKey<Level> DYSTOPIA = ResourceKey.create(Registries.DIMENSION, OutlandHorizon.createModResourceLocation("dystopia"));
}
