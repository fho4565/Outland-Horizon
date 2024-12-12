package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.block.*;
import com.arc.outland_horizon.world.block.fluids.blood.*;
import com.arc.outland_horizon.world.block.logs.OHLogBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class OHBlocks {
    private static RegistryObject<Block> deepOre(String id, float destroyTime, float explosionResistance) {
        return BlockRegistry.register("deep_" + id + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.DEEPSLATE).strength(destroyTime, explosionResistance).requiresCorrectToolForDrops()));
    }

    private static RegistryObject<Block> ore(String id, float destroyTime, float explosionResistance) {
        return BlockRegistry.register(id + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(destroyTime, explosionResistance).requiresCorrectToolForDrops()));
    }

    private static RegistryObject<Block> oreBlock(String id, float destroyTime, float explosionResistance) {
        return BlockRegistry.register(id + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(destroyTime, explosionResistance).requiresCorrectToolForDrops()));
    }

    private static ButtonBlock woodenButton(BlockSetType pSetType, FeatureFlag... pRequiredFeatures) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (pRequiredFeatures.length > 0) {
            properties = properties.requiredFeatures(pRequiredFeatures);
        }

        return new ButtonBlock(properties, pSetType, 30, true);
    }

    public static void init() {
        Fluids.init();
        Building.init();
        Functional.init();
        Natural.init();
    }

    public static class Building {
        public static void init() {
            DUNGEON.init();
            NIGHTMARE.init();
        }

        public static class NIGHTMARE {
            public static final RegistryObject<Block> STRIPPED_NIGHTMARE_LOG = BlockRegistry.register("stripped_nightmare_log", () -> new OHLogBlock(Block.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> NIGHTMARE_LOG = BlockRegistry.register("nightmare_log", () -> new OHLogBlock(Block.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).sound(SoundType.WOOD).ignitedByLava(), STRIPPED_NIGHTMARE_LOG.get()));
            public static final RegistryObject<Block> NIGHTMARE_WOOD = BlockRegistry.register("nightmare_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> STRIPPED_NIGHTMARE_WOOD = BlockRegistry.register("stripped_nightmare_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));

            public static final RegistryObject<Block> NIGHTMARE_PLANKS = BlockRegistry.register("nightmare_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> NIGHTMARE_PRESSURE_PLATE = BlockRegistry.register("nightmare_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(NIGHTMARE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
            public static final RegistryObject<Block> NIGHTMARE_TRAPDOOR = BlockRegistry.register("nightmare_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false).ignitedByLava(), BlockSetType.OAK));
            public static final RegistryObject<Block> NIGHTMARE_BUTTON = BlockRegistry.register("nightmare_button", () -> woodenButton(BlockSetType.OAK));
            public static final RegistryObject<Block> NIGHTMARE_STAIRS = BlockRegistry.register("nightmare_stairs", () -> new StairBlock(() -> NIGHTMARE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(NIGHTMARE_PLANKS.get())));
            public static final RegistryObject<Block> NIGHTMARE_SLAB = BlockRegistry.register("nightmare_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> NIGHTMARE_FENCE_GATE = BlockRegistry.register("nightmare_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(NIGHTMARE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), WoodType.OAK));
            public static final RegistryObject<Block> NIGHTMARE_FENCE = BlockRegistry.register("nightmare_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(NIGHTMARE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
            public static final RegistryObject<Block> NIGHTMARE_DOOR = BlockRegistry.register("nightmare_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(NIGHTMARE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));

            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_PLANKS = BlockRegistry.register("coagulated_nightmare_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_PRESSURE_PLATE = BlockRegistry.register("coagulated_nightmare_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(COAGULATED_NIGHTMARE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_TRAPDOOR = BlockRegistry.register("coagulated_nightmare_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false).ignitedByLava(), BlockSetType.OAK));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_BUTTON = BlockRegistry.register("coagulated_nightmare_button", () -> woodenButton(BlockSetType.OAK));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_STAIRS = BlockRegistry.register("coagulated_nightmare_stairs", () -> new StairBlock(() -> COAGULATED_NIGHTMARE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(COAGULATED_NIGHTMARE_PLANKS.get())));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_SLAB = BlockRegistry.register("coagulated_nightmare_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_FENCE_GATE = BlockRegistry.register("coagulated_nightmare_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(COAGULATED_NIGHTMARE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), WoodType.OAK));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_FENCE = BlockRegistry.register("coagulated_nightmare_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(COAGULATED_NIGHTMARE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
            public static final RegistryObject<Block> COAGULATED_NIGHTMARE_DOOR = BlockRegistry.register("coagulated_nightmare_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(COAGULATED_NIGHTMARE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));


            public static final RegistryObject<Block> NIGHTMARE_STONE = BlockRegistry.register("nightmare_stone", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)));
            public static final RegistryObject<Block> FLESH_BLOCK = BlockRegistry.register("flesh_block", () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .sound(new ForgeSoundType(0.5f, 1
                            , SoundType.MUD::getBreakSound
                            , SoundType.MUD::getStepSound
                            , SoundType.MUD::getPlaceSound
                            , () -> SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("flesh_block_breaking"))
                            , SoundType.MUD::getFallSound
                    ))
                    .requiresCorrectToolForDrops()
                    .strength(3.5F, 12.0F)));
            public static final RegistryObject<Block> SCARRED_FLESH_BLOCK = BlockRegistry.register("scarred_flesh_block", () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .sound(new ForgeSoundType(0.5f, 1
                            , SoundType.MUD::getBreakSound
                            , SoundType.MUD::getStepSound
                            , SoundType.MUD::getPlaceSound
                            , () -> SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("flesh_block_breaking"))
                            , SoundType.MUD::getFallSound
                    ))
                    .requiresCorrectToolForDrops()
                    .strength(3.2F, 11.0F)));

            public static void init() {
            }
        }

        public static class DUNGEON {
            private static final BlockBehaviour.Properties PROPERTIES = BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .sound(SoundType.NETHER_BRICKS)
                    .requiresCorrectToolForDrops()
                    .strength(150.0F, 600.0F);

            public static final RegistryObject<Block> DUNGEON_BRICK = BlockRegistry.register("dungeon_brick",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_STAIR = BlockRegistry.register("dungeon_brick_stairs",
                    () -> new StairBlock(() -> DUNGEON.DUNGEON_BRICK.get().defaultBlockState(), PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_TILE = BlockRegistry.register("dungeon_brick_tile",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_PILLAR = BlockRegistry.register("dungeon_brick_pillar",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> ZOMBIE_DUNGEON_BRICK = BlockRegistry.register("zombie_dungeon_brick",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> SKELETON_DUNGEON_BRICK = BlockRegistry.register("skeleton_dungeon_brick",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> DAMAGED_DUNGEON_BRICK = BlockRegistry.register("damaged_dungeon_brick",
                    () -> new DamagedDungeonBrick(PROPERTIES.explosionResistance(0).noOcclusion()));
            public static final RegistryObject<Block> WORN_DUNGEON_BRICK = BlockRegistry.register("worn_dungeon_brick",
                    () -> new Block(PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_SLAB = BlockRegistry.register("dungeon_brick_slab",
                    () -> new SlabBlock(PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_SIDE_SLAB = BlockRegistry.register("dungeon_brick_side_slab",
                    () -> new SideSlab(PROPERTIES));
            public static final RegistryObject<Block> DUNGEON_BRICK_WALL = BlockRegistry.register("dungeon_brick_wall",
                    () -> new WallBlock(PROPERTIES.forceSolidOn()));


            public static void init() {
            }
        }
    }

    public static class Functional {
        public static final RegistryObject<Block> TEXTURES_TEST_BLOCK = BlockRegistry.register("textures_test_block", () -> new TexturesTestBlock(Block.Properties.copy(Blocks.STONE)), true, false);
        public static final RegistryObject<Block> DUNGEON_TORCH = BlockRegistry.BLOCKS.register("dungeon_torch", () -> new TorchBlock(Block.Properties.copy(Blocks.TORCH), ParticleTypes.FLAME));
        public static final RegistryObject<Block> WALL_DUNGEON_TORCH = BlockRegistry.BLOCKS.register("wall_dungeon_torch", () -> new WallTorchBlock(Block.Properties.copy(Blocks.WALL_TORCH), ParticleTypes.FLAME));

        public static void init() {
        }
    }

    public static class Natural {
        public static final RegistryObject<Block> NIGHTMARE_DIRT = BlockRegistry.register("nightmare_dirt", NightmareDirt::new);
        public static final RegistryObject<Block> SAPLINGS = BlockRegistry.register("saplings", Saplings::new);
        public static final RegistryObject<Block> BLOOD_STONE_BLOCK = oreBlock("blood_stone", 6.0f, 9.0f);
        public static RegistryObject<Block> BLUE_GEM_ORE = ore("blue_gem", 3.0f, 3.0f);
        public static RegistryObject<Block> DEEP_BLUE_GEM_ORE = deepOre("blue_gem", 4.5f, 3.0f);
        public static RegistryObject<Block> BLUE_GEM_BLOCK = oreBlock("blue_gem", 5.0f, 6.0f);
        public static RegistryObject<Block> BLOOD_STONE_ORE = ore("blood_stone", 5.0f, 8.0f);

        public static void init() {
            PaleAbyss.init();
        }

        public static class PaleAbyss {
            public static final RegistryObject<Block> PALE_ABYSS_STONE = BlockRegistry.register("pale_abyss_stone", () -> new Block(
                    BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                            .strength(12.0F, 24.0F)

            ));
            public static final RegistryObject<Block> CONDENSED_ORE = BlockRegistry.register("condensed_ore", () -> new Block(
                    BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
                            .strength(24.0F, 36.0F)
            ), true, Rarity.UNCOMMON);

            public static void init() {
            }
        }
    }

    public static class Fluids {
        public static void init() {
            FluidBlocks.init();
            OHFluidTypes.init();
            OHFluids.init();
        }

        public static class FluidBlocks {
            public static final RegistryObject<Block> BLOOD_BLOCK = BlockRegistry.register("blood", BloodBlock::new);
            public static final RegistryObject<Block> Arterial_BLOOD_BLOCK = BlockRegistry.register("arterial_blood", ArterialBloodBlock::new);

            public static void init() {
            }
        }

        public static class OHFluidTypes {
            public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, OutlandHorizon.MOD_ID);
            public static final RegistryObject<FluidType> BLOOD_TYPE = FLUID_TYPES.register("blood_type", BloodType::new);
            public static final RegistryObject<FluidType> ArterialBLOOD_TYPE = FLUID_TYPES.register("arterial_blood_type", ArterialBloodType::new);

            public static void init() {
            }
        }

        public static class OHFluids {
            public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, OutlandHorizon.MOD_ID);
            public static final RegistryObject<FlowingFluid> BLOOD = FLUIDS.register("blood", Blood.Source::new);
            public static final RegistryObject<FlowingFluid> BLOOD_FLOWING = FLUIDS.register("blood_flowing", Blood.Flowing::new);

            public static final RegistryObject<FlowingFluid> ArterialBLOOD = FLUIDS.register("arterial_blood", ArterialBlood.Source::new);
            public static final RegistryObject<FlowingFluid> ArterialBLOOD_FLOWING = FLUIDS.register("arterial_blood_flowing", ArterialBlood.Flowing::new);

            public static void init() {
            }
        }
    }
}
