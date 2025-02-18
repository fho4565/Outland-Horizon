package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.core.LogPack;
import com.arc.outland_horizon.core.ModPacks;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.Curios;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ModTag {

    public static class ItemTag extends TagsProvider<Item> {
        public ItemTag(PackOutput pOutput, ResourceKey<? extends Registry<Item>> pRegistryKey, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pRegistryKey, pLookupProvider, OutlandHorizon.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            addItemTags(new ResourceLocation(Curios.MODID, "body"),
                    OHItems.Ornament.ZOMBIE_MEDAL_COPPER.get(),
                    OHItems.Ornament.ZOMBIE_MEDAL_SILVER.get(),
                    OHItems.Ornament.ZOMBIE_MEDAL_GOLD.get()
            );
            addItemTags(new ResourceLocation(Curios.MODID, "charm"), OHItems.Ornament.BRIGHT_TALISMAN.get());
        }

        private void addItemTags(ResourceLocation resourceLocation, Item... items) {
            TagAppender<Item> tag = tag(TagKey.create(Registries.ITEM, resourceLocation));
            for (Item block : items) {
                ForgeRegistries.ITEMS.getResourceKey(block).ifPresent(tag::add);
            }
        }

    }

    public static class BlockTag extends TagsProvider<Block> {

        public BlockTag(PackOutput pOutput, ResourceKey<? extends Registry<Block>> pRegistryKey, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pRegistryKey, pLookupProvider, OutlandHorizon.MOD_ID, existingFileHelper);
        }


        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            logPack(ModPacks.NIGHTMARE_LOG);
            logPack(ModPacks.COAGULATED_NIGHTMARE_LOG);

            addBlockTags(OutlandHorizon.createModResourceLocation("dungeon"),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_TILE.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_WALL.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_STAIRS.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_SLAB.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_PILLAR.get(),
                    OHBlocks.Building.DUNGEON.DUNGEON_BRICK_SIDE_SLAB.get(),
                    OHBlocks.Building.DUNGEON.ZOMBIE_DUNGEON_BRICK.get(),
                    OHBlocks.Building.DUNGEON.SKELETON_DUNGEON_BRICK.get(),
                    OHBlocks.Building.DUNGEON.WORN_DUNGEON_BRICK.get(),
                    OHBlocks.Building.DUNGEON.DAMAGED_DUNGEON_BRICK.get()
            );
        }

        private void addBlockTags(ResourceLocation resourceLocation, Block... blocks) {
            TagAppender<Block> tag = tag(TagKey.create(Registries.BLOCK, resourceLocation));
            for (Block block : blocks) {
                ForgeRegistries.BLOCKS.getResourceKey(block).ifPresent(tag::add);
            }
        }

        private void addBlockTag(ResourceLocation resourceLocation, Block block) {
            ForgeRegistries.BLOCKS.getResourceKey(block).ifPresent(blockResourceKey -> tag(TagKey.create(Registries.BLOCK, resourceLocation)).add(blockResourceKey));
        }

        private void logPack(LogPack logPack) {
            Block logBlock = logPack.get(LogPack.Variant.LOG);
            if (logBlock != null) {
                addBlockTag(BlockTags.LOGS.location(), logBlock);
            }
            addBlockTag(BlockTags.PLANKS.location(), logPack.get(LogPack.Variant.PLANKS));
            addBlockTag(BlockTags.WOODEN_FENCES.location(), logPack.get(LogPack.Variant.FENCE));
            addBlockTag(BlockTags.FENCE_GATES.location(), logPack.get(LogPack.Variant.FENCE_GATE));
            addBlockTag(BlockTags.WOODEN_STAIRS.location(), logPack.get(LogPack.Variant.STAIRS));
            addBlockTag(BlockTags.WOODEN_SLABS.location(), logPack.get(LogPack.Variant.SLAB));
            addBlockTag(BlockTags.WOODEN_BUTTONS.location(), logPack.get(LogPack.Variant.BUTTON));
            addBlockTag(BlockTags.WOODEN_DOORS.location(), logPack.get(LogPack.Variant.DOOR));
            addBlockTag(BlockTags.WOODEN_TRAPDOORS.location(), logPack.get(LogPack.Variant.TRAPDOOR));
            addBlockTag(BlockTags.WOODEN_PRESSURE_PLATES.location(), logPack.get(LogPack.Variant.PRESSURE_PLATE));
        }
    }
}
