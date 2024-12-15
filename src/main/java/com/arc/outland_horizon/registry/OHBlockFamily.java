package com.arc.outland_horizon.registry;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.stream.Stream;

public class OHBlockFamily {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";

    public static final BlockFamily NIGHTMARE_LOG = familyBuilder(OHBlocks.Building.NIGHTMARE.NIGHTMARE_PLANKS.get()).button(OHBlocks.Building.NIGHTMARE.NIGHTMARE_BUTTON.get()).fence(OHBlocks.Building.NIGHTMARE.NIGHTMARE_FENCE.get()).fenceGate(OHBlocks.Building.NIGHTMARE.NIGHTMARE_FENCE_GATE.get()).sign(Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN).pressurePlate(OHBlocks.Building.NIGHTMARE.NIGHTMARE_PRESSURE_PLATE.get()).slab(OHBlocks.Building.NIGHTMARE.NIGHTMARE_SLAB.get()).stairs(OHBlocks.Building.NIGHTMARE.NIGHTMARE_STAIRS.get()).door(OHBlocks.Building.NIGHTMARE.NIGHTMARE_DOOR.get()).trapdoor(OHBlocks.Building.NIGHTMARE.NIGHTMARE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily COAGULATED_NIGHTMARE_LOG = familyBuilder(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_PLANKS.get()).button(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_BUTTON.get()).fence(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_FENCE.get()).fenceGate(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_FENCE_GATE.get()).sign(Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN).pressurePlate(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_PRESSURE_PLATE.get()).slab(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_SLAB.get()).stairs(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_STAIRS.get()).door(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_DOOR.get()).trapdoor(OHBlocks.Building.NIGHTMARE.COAGULATED_NIGHTMARE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();


    private static BlockFamily.Builder familyBuilder(Block pBaseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(pBaseBlock);
        BlockFamily blockfamily = MAP.put(pBaseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(pBaseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
