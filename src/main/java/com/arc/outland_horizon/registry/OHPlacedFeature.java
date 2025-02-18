package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class OHPlacedFeature {
    public static final ResourceKey<PlacedFeature> NIGHTMARE_TREE = createKey("nightmare_tree_checked");

    public static void boostrap(final BootstapContext<PlacedFeature> context) {
        register(context, NIGHTMARE_TREE, OHConfiguredFeature.LEMON_TREE, checkTree(OHBlocks.Natural.SAPLINGS));
    }

    private static ResourceKey<PlacedFeature> createKey(final String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(OutlandHorizon.MOD_ID, name));
    }

    public static Holder.Reference<PlacedFeature> register(final BootstapContext<PlacedFeature> context, final ResourceKey<PlacedFeature> key, final ResourceKey<ConfiguredFeature<?, ?>> featureKey, final List<PlacementModifier> placement) {
        final Holder<ConfiguredFeature<?, ?>> feature = context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(featureKey);
        return context.register(key, new PlacedFeature(feature, placement));
    }

    private static List<PlacementModifier> checkTree(RegistryObject<? extends Block> sapling) {
        return List.of(saplingFilter(sapling));
    }

    private static BlockPredicateFilter saplingFilter(RegistryObject<? extends Block> sapling) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(sapling.get().defaultBlockState(), BlockPos.ZERO));
    }
}
