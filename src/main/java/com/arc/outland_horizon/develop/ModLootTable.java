package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.BlockRegistry;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.arc.outland_horizon.registry.OHBlocks.Building.DUNGEON.*;
import static com.arc.outland_horizon.registry.OHBlocks.Building.NIGHTMARE.*;
import static com.arc.outland_horizon.registry.OHBlocks.Functional.DUNGEON_TORCH;
import static com.arc.outland_horizon.registry.OHBlocks.Functional.WALL_DUNGEON_TORCH;
import static com.arc.outland_horizon.registry.OHBlocks.Natural.*;
import static com.arc.outland_horizon.registry.OHBlocks.Natural.PaleAbyss.CONDENSED_ORE;
import static com.arc.outland_horizon.registry.OHBlocks.Natural.PaleAbyss.PALE_ABYSS_STONE;

public class ModLootTable extends LootTableProvider {
    public ModLootTable(PackOutput pOutput) {
        super(pOutput, Collections.emptySet(),
                List.of(
                        new SubProviderEntry(ModBlockLootTable::new, LootContextParamSets.BLOCK)
                )
        );
    }

    public static class ModBlockLootTable extends BlockLootSubProvider {
        private static class Init {
            private static void init(ModBlockLootTable lootTable) {
                Building.init(lootTable);
                Natural.init(lootTable);
                Functional.init(lootTable);
            }

            static class Building {
                private static void init(ModBlockLootTable lootTable) {
                    Nightmare.init(lootTable);
                    Dungeon.init(lootTable);
                }

                static class Nightmare {
                    private static void init(ModBlockLootTable lootTable) {
                        lootTable.dropSelf(STRIPPED_NIGHTMARE_LOG.get());
                        lootTable.dropSelf(NIGHTMARE_LOG.get());
                        lootTable.dropSelf(NIGHTMARE_WOOD.get());
                        lootTable.dropSelf(STRIPPED_NIGHTMARE_WOOD.get());
                        lootTable.dropSelf(NIGHTMARE_PLANKS.get());
                        lootTable.dropSelf(NIGHTMARE_PRESSURE_PLATE.get());
                        lootTable.dropSelf(NIGHTMARE_TRAPDOOR.get());
                        lootTable.dropSelf(NIGHTMARE_BUTTON.get());
                        lootTable.dropSelf(NIGHTMARE_STAIRS.get());
                        lootTable.dropSelf(NIGHTMARE_SLAB.get());
                        lootTable.dropSelf(NIGHTMARE_FENCE_GATE.get());
                        lootTable.dropSelf(NIGHTMARE_FENCE.get());
                        lootTable.add(NIGHTMARE_DOOR.get(), lootTable.createDoorTable(NIGHTMARE_DOOR.get()));
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_PLANKS.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_PRESSURE_PLATE.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_TRAPDOOR.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_BUTTON.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_STAIRS.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_SLAB.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_FENCE_GATE.get());
                        lootTable.dropSelf(COAGULATED_NIGHTMARE_FENCE.get());
                        lootTable.add(COAGULATED_NIGHTMARE_DOOR.get(), lootTable.createDoorTable(COAGULATED_NIGHTMARE_DOOR.get()));
                        lootTable.dropSelf(NIGHTMARE_STONE.get());
                        lootTable.add(FLESH_BLOCK.get(), createSilkTouchDispatchTable(FLESH_BLOCK.get(), LootItem.lootTableItem(SCARRED_FLESH_BLOCK.get())));
                        lootTable.dropSelf(SCARRED_FLESH_BLOCK.get());
                    }
                }

                static class Dungeon {
                    static void init(ModBlockLootTable lootTable) {
                        lootTable.dropSelf(DUNGEON_BRICK.get());
                        lootTable.dropSelf(DUNGEON_BRICK_STAIR.get());
                        lootTable.dropSelf(DUNGEON_BRICK_TILE.get());
                        lootTable.dropSelf(DUNGEON_BRICK_PILLAR.get());
                        lootTable.dropSelf(ZOMBIE_DUNGEON_BRICK.get());
                        lootTable.dropSelf(SKELETON_DUNGEON_BRICK.get());
                        lootTable.dropSelf(DAMAGED_DUNGEON_BRICK.get());
                        lootTable.dropSelf(WORN_DUNGEON_BRICK.get());
                        lootTable.dropSelf(DUNGEON_BRICK_SLAB.get());
                        lootTable.dropSelf(DUNGEON_BRICK_SIDE_SLAB.get());
                        lootTable.dropSelf(DUNGEON_BRICK_WALL.get());
                    }
                }
            }

            public static class Natural {
                public static void init(ModBlockLootTable lootTable) {
                    PaleAbyss.init(lootTable);
                    Nightmare.init(lootTable);
                }

                static class Nightmare {
                    static void init(ModBlockLootTable lootTable) {
                        lootTable.dropSelf(NIGHTMARE_DIRT.get());
                        lootTable.add(SAPLINGS.get(), noDrop());
                        lootTable.dropSelf(BLOOD_STONE_BLOCK.get());
                        lootTable.dropSelf(BLUE_GEM_BLOCK.get());
                        lootTable.add(BLUE_GEM_ORE.get(), lootTable.createOreDrop(BLUE_GEM_ORE.get(), OHItems.Material.BLUE_GEM.get()));
                        lootTable.add(DEEP_BLUE_GEM_ORE.get(), lootTable.createOreDrop(DEEP_BLUE_GEM_ORE.get(), OHItems.Material.BLUE_GEM.get()));
                        lootTable.add(BLOOD_STONE_ORE.get(), lootTable.createOreDrop(BLOOD_STONE_ORE.get(), OHItems.Material.BLOOD_STONE.get()));
                    }
                }

                public static class PaleAbyss {
                    public static void init(ModBlockLootTable lootTable) {
                        lootTable.dropSelf(PALE_ABYSS_STONE.get());
                        lootTable.add(CONDENSED_ORE.get(), lootTable.createOreDrop(CONDENSED_ORE.get(), OHItems.Material.CONDENSED_CRYSTAL.get()));
                    }
                }
            }

            public static class Functional {

                public static void init(ModBlockLootTable lootTable) {
                    lootTable.dropSelf(DUNGEON_TORCH.get());
                    lootTable.dropSelf(WALL_DUNGEON_TORCH.get());
                }
            }
        }

        public ModBlockLootTable() {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<RegistryObject<Block>> entries = new ArrayList<>(BlockRegistry.BLOCKS.getEntries());
            entries.removeIf(blockRegistryObject -> blockRegistryObject.equals(OHBlocks.Functional.TEXTURES_TEST_BLOCK));
            return entries
                    .stream()
                    .flatMap(RegistryObject::stream)
                    ::iterator;
        }

        @Override
        protected void generate() {
            Init.init(this);
        }

        protected LootTable.Builder createMultiItemTable(List<ItemStack> items) {
            LootPool.Builder builder = new LootPool.Builder().setRolls(ConstantValue.exactly(1));
            items.forEach(itemStack -> builder.add(LootItem.lootTableItem(itemStack.getItem()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(itemStack.getCount())))));
            return LootTable.lootTable().withPool(builder);
        }
    }

}
