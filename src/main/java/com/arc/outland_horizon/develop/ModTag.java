package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHBlockFamily;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.Utils;
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
            this.tag(TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "body")))
                    .add(
                            OHItems.Ornament.ZOMBIE_MEDAL_COPPER.getKey(),
                            OHItems.Ornament.ZOMBIE_MEDAL_SILVER.getKey(),
                            OHItems.Ornament.ZOMBIE_MEDAL_GOLD.getKey()
                    );
            this.tag(TagKey.create(Registries.ITEM, new ResourceLocation(Curios.MODID, "charm")))
                    .add(
                            OHItems.Ornament.BRIGHT_TALISMAN.getKey()
                    );
        }

    }

    public static class BlockTag extends TagsProvider<Block> {

        public BlockTag(PackOutput pOutput, ResourceKey<? extends Registry<Block>> pRegistryKey, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pRegistryKey, pLookupProvider, OutlandHorizon.MOD_ID, existingFileHelper);
        }


        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            TagAppender<Block> tag = this.tag(TagKey.create(Registries.BLOCK, BlockTags.PLANKS.location()));
            OHBlockFamily.getAllFamilies()
                    .map(blockFamily ->
                            ResourceKey.create(Registries.BLOCK, OutlandHorizon.createModResourceLocation(Utils.getDescriptionIdName(blockFamily.getBaseBlock()))))
                    .forEach(tag::add);
        }
    }
}
