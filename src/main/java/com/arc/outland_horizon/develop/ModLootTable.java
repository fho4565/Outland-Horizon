package com.arc.outland_horizon.develop;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Collections;

public class ModLootTable {
    public static class ModBlockLootTable extends BlockLootSubProvider {
        protected ModBlockLootTable() {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {

        }
    }

    public static class ModEntityLootTable extends EntityLootSubProvider {
        protected ModEntityLootTable() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {

        }
    }
}
