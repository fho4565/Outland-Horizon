package com.arc.outland_horizon.world.item.tools;

import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeTier;

import javax.annotation.Nonnull;

public class DungeonDestroyer extends PickaxeItem {

    public DungeonDestroyer() {
        super(new ForgeTier(2, 100, 64.0f, 2, 14, OHTags.Blocks.DUNGEON, () -> Ingredient.of(new DungeonDestroyer())), 2, -2.8f, new Item.Properties().rarity(Rarity.EPIC));
    }

    private static boolean isDungeonBlock(BlockState state) {
        return state.is(OHBlocks.Building.DUNGEON.DUNGEON_BRICK.get()) ||
                state.is(OHBlocks.Building.DUNGEON.DAMAGED_DUNGEON_BRICK.get()) ||
                state.is(OHBlocks.Building.DUNGEON.WORN_DUNGEON_BRICK.get()) ||
                state.is(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_PILLAR.get()) ||
                state.is(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_TILE.get()) ||
                state.is(OHBlocks.Building.DUNGEON.ZOMBIE_DUNGEON_BRICK.get()) ||
                state.is(OHBlocks.Building.DUNGEON.SKELETON_DUNGEON_BRICK.get());
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack pStack, @Nonnull BlockState pState) {
        return isDungeonBlock(pState) ? 64.0f : 1.0f;
    }

    @Override
    public boolean isCorrectToolForDrops(@Nonnull ItemStack stack, @Nonnull BlockState state) {
        return isDungeonBlock(state);
    }
}
