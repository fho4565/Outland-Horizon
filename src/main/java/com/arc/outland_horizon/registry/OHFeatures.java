package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.features.nightmare.NightmareTree;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OHFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, OutlandHorizon.MOD_ID);

    public static final RegistryObject<NightmareTree> NIGHTMARE_TREE = register("nightmare_tree", NightmareTree::new);

    public static <T extends Feature<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return FEATURES.register(name, sup);
    }

    public static <F extends Feature<NoneFeatureConfiguration>> Holder.Reference<ConfiguredFeature<?, ?>> registerTo(final BootstapContext<ConfiguredFeature<?, ?>> context, final ResourceKey<ConfiguredFeature<?, ?>> key, final RegistryObject<F> feature) {
        return context.register(key, new ConfiguredFeature<>(feature.get(), NoneFeatureConfiguration.INSTANCE));
    }

    public static void bootstrap(final BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerTo(context, OHConfiguredFeature.LEMON_TREE, NIGHTMARE_TREE);
    }

}
