package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class OHTags {
    public static class Blocks {
        public static final TagKey<Block> DUNGEON = tag("dungeon");//地牢方块

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(OutlandHorizon.createModResourceLocation(name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SNIPER_RIFLE = tag("sniper_rifle");//机枪

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(OutlandHorizon.createModResourceLocation(name));
        }
    }
}
