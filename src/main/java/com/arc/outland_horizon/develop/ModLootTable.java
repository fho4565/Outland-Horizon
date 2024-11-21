package com.arc.outland_horizon.develop;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class ModLootTable extends LootTableProvider {
    public ModLootTable(PackOutput pOutput) {
        super(pOutput, Collections.emptySet(), List.of(new SubProviderEntry(ModBlockLootTable::new, LootContextParamSets.BLOCK), new SubProviderEntry(ModEntityLootTable::new, LootContextParamSets.ENTITY)));
    }

    public static class ModBlockLootTable extends BlockLootSubProvider {
        public ModBlockLootTable() {
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
