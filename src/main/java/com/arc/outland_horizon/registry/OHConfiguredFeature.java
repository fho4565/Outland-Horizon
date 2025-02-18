package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class OHConfiguredFeature {
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEMON_TREE = createKey("nightmare_tree");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(OutlandHorizon.MOD_ID, name));
    }
}
