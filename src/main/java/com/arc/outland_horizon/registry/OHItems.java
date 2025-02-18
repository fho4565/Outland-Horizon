package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.core.ModArmorMaterials;
import com.arc.outland_horizon.core.ModTiers;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.item.consumables.potions.MadPotion;
import com.arc.outland_horizon.world.item.consumables.potions.ManaPotion;
import com.arc.outland_horizon.world.item.ornaments.medal.ZombieMedal;
import com.arc.outland_horizon.world.item.ornaments.talisman.Bright;
import com.arc.outland_horizon.world.item.tools.DungeonDestroyer;
import com.arc.outland_horizon.world.item.tools.ZeroReformer;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.PaxelItem;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import com.arc.outland_horizon.world.item.utility.DamoclesSword;
import com.arc.outland_horizon.world.item.weapons.magic.book.ShieldBook;
import com.arc.outland_horizon.world.item.weapons.magic.wand.FireWand;
import com.arc.outland_horizon.world.item.weapons.melee.sickle.ZenithCrimsonDemise;
import com.arc.outland_horizon.world.item.weapons.melee.sword.BlazeSword;
import com.arc.outland_horizon.world.item.weapons.melee.sword.Debugger;
import com.arc.outland_horizon.world.item.weapons.melee.sword.Elegy;
import com.arc.outland_horizon.world.item.weapons.ranged.gun.*;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nonnull;

import static com.arc.outland_horizon.registry.ItemRegistry.registerMaterial;

public class OHItems {
    public static void initCurio() {
        CuriosApi.registerCurio(Ornament.ZOMBIE_MEDAL_COPPER.get(), new ZombieMedal.Copper());
        CuriosApi.registerCurio(Ornament.ZOMBIE_MEDAL_SILVER.get(), new ZombieMedal.Silver());
        CuriosApi.registerCurio(Ornament.ZOMBIE_MEDAL_GOLD.get(), new ZombieMedal.Gold());
        CuriosApi.registerCurio(Ornament.BRIGHT_TALISMAN.get(), new Bright());
    }

    public static void init() {
        Material.init();
        Consumable.init();
        Tool.init();
        Weapon.init();
        Armor.init();
        BlockItem.init();
        Utilities.init();
        Ornament.init();
    }

    public static class Consumable {
        public static final RegistryObject<Item> MANA_POTION_COMMON = ItemRegistry.registerConsumables("mana_potion.common", ManaPotion.Common::new);
        public static final RegistryObject<Item> MANA_POTION_INTERMEDIATE = ItemRegistry.registerConsumables("mana_potion.intermediate", ManaPotion.Intermediate::new);
        public static final RegistryObject<Item> MANA_POTION_ADVANCED = ItemRegistry.registerConsumables("mana_potion.advanced", ManaPotion.Advanced::new);
        public static final RegistryObject<Item> MANA_POTION_SUPER = ItemRegistry.registerConsumables("mana_potion.super", ManaPotion.Super::new);
        public static final RegistryObject<Item> MANA_POTION_ULTIMATE = ItemRegistry.registerConsumables("mana_potion.ultimate", ManaPotion.Ultimate::new);
        public static final RegistryObject<Item> RAGE_POTION = ItemRegistry.registerConsumables("mad_potion", MadPotion::new);
        public static final RegistryObject<Item> PlatterOfMeatsRaw = ItemRegistry.registerConsumables("platter_of_raw_meats", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(10).saturationMod(6.0f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, Utils.secondsToTicks(10)), 0.6f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, Utils.secondsToTicks(30), 1), 1.0f).build())));
        public static final RegistryObject<Item> PlatterOfMeatsCooked = ItemRegistry.registerConsumables("platter_of_cooked_meats", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .meat()
                .nutrition(28).saturationMod(42.4f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Utils.secondsToTicks(30)), 1.0f)
                .build())));

        private static void init() {
        }
    }

    public static class Tool {
        public static final RegistryObject<Item> BLOOD_BUCKET = ItemRegistry.registerTool("blood_bucket", () -> new BucketItem(OHBlocks.Fluids.OHFluids.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        public static final RegistryObject<Item> ARTERIAL_BLOOD_BUCKET = ItemRegistry.registerTool("arterial_blood_bucket", () -> new BucketItem(OHBlocks.Fluids.OHFluids.ArterialBLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        public static final RegistryObject<Item> ZERO_REFORMER = ItemRegistry.registerTool("zero_reformer", ZeroReformer::new);
        public static final RegistryObject<Item> DUNGEON_DESTROYER = ItemRegistry.registerTool("dungeon_destroyer", DungeonDestroyer::new);

        public static final RegistryObject<Item> BLUE_GEM_PICKAXE = ItemRegistry.registerTool("blue_gem_pickaxe", () -> new PickaxeItem(ModTiers.BLUE_GEM, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_AXE = ItemRegistry.registerTool("blue_gem_axe", () -> new AxeItem(ModTiers.BLUE_GEM, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SHOVEL = ItemRegistry.registerTool("blue_gem_shovel", () -> new ShovelItem(ModTiers.BLUE_GEM, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HOE = ItemRegistry.registerTool("blue_gem_hoe", () -> new HoeItem(ModTiers.BLUE_GEM, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_PAXEL = ItemRegistry.registerTool("blue_gem_paxel", () -> new PaxelItem(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HAMMER = ItemRegistry.registerTool("blue_gem_hammer", () -> new Hammer(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SPADE = ItemRegistry.registerTool("blue_gem_spade", () -> new Spade(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_DESTROYER = ItemRegistry.registerTool("blue_gem_destroyer", () -> new Destroyer(ModTiers.BLUE_GEM, new Item.Properties()));

        public static final RegistryObject<Item> BLOOD_STONE_PICKAXE = ItemRegistry.registerTool("blood_stone_pickaxe", () -> new PickaxeItem(ModTiers.BLOOD_STONE, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_AXE = ItemRegistry.registerTool("blood_stone_axe", () -> new AxeItem(ModTiers.BLOOD_STONE, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SHOVEL = ItemRegistry.registerTool("blood_stone_shovel", () -> new ShovelItem(ModTiers.BLOOD_STONE, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HOE = ItemRegistry.registerTool("blood_stone_hoe", () -> new HoeItem(ModTiers.BLOOD_STONE, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_PAXEL = ItemRegistry.registerTool("blood_stone_paxel", () -> new PaxelItem(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HAMMER = ItemRegistry.registerTool("blood_stone_hammer", () -> new Hammer(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SPADE = ItemRegistry.registerTool("blood_stone_spade", () -> new Spade(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_DESTROYER = ItemRegistry.registerTool("blood_stone_destroyer", () -> new Destroyer(ModTiers.BLOOD_STONE, new Item.Properties()));

        public static final RegistryObject<Item> WOODEN_PAXEL = ItemRegistry.registerTool("wooden_paxel", () -> new PaxelItem(new Tier() {
            @Override
            public int getUses() {
                return Math.round(Tiers.WOOD.getUses() * 2.5f);
            }

            @Override
            public float getSpeed() {
                return Tiers.WOOD.getSpeed() * 0.9f;
            }

            @Override
            public float getAttackDamageBonus() {
                return Tiers.WOOD.getAttackDamageBonus();
            }

            @Override
            public int getLevel() {
                return Tiers.WOOD.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return Tiers.WOOD.getEnchantmentValue();
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Tiers.WOOD.getRepairIngredient();
            }
        }, new Item.Properties()));

        public static final RegistryObject<Item> STONE_PAXEL = ItemRegistry.registerTool("stone_paxel", () -> new PaxelItem(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_HAMMER = ItemRegistry.registerTool("stone_hammer", () -> new Hammer(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_SPADE = ItemRegistry.registerTool("stone_spade", () -> new Spade(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_DESTROYER = ItemRegistry.registerTool("stone_destroyer", () -> new Destroyer(Tiers.STONE, new Item.Properties()));

        public static final RegistryObject<Item> IRON_PAXEL = ItemRegistry.registerTool("iron_paxel", () -> new PaxelItem(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_HAMMER = ItemRegistry.registerTool("iron_hammer", () -> new Hammer(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_SPADE = ItemRegistry.registerTool("iron_spade", () -> new Spade(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_DESTROYER = ItemRegistry.registerTool("iron_destroyer", () -> new Destroyer(Tiers.IRON, new Item.Properties()));

        public static final RegistryObject<Item> GOLDEN_PAXEL = ItemRegistry.registerTool("golden_paxel", () -> new PaxelItem(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_HAMMER = ItemRegistry.registerTool("golden_hammer", () -> new Hammer(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_SPADE = ItemRegistry.registerTool("golden_spade", () -> new Spade(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_DESTROYER = ItemRegistry.registerTool("golden_destroyer", () -> new Destroyer(Tiers.GOLD, new Item.Properties()));

        public static final RegistryObject<Item> DIAMOND_PAXEL = ItemRegistry.registerTool("diamond_paxel", () -> new PaxelItem(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_HAMMER = ItemRegistry.registerTool("diamond_hammer", () -> new Hammer(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_SPADE = ItemRegistry.registerTool("diamond_spade", () -> new Spade(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_DESTROYER = ItemRegistry.registerTool("diamond_destroyer", () -> new Destroyer(Tiers.DIAMOND, new Item.Properties()));

        public static final RegistryObject<Item> NETHERITE_PAXEL = ItemRegistry.registerTool("netherite_paxel", () -> new PaxelItem(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_HAMMER = ItemRegistry.registerTool("netherite_hammer", () -> new Hammer(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_SPADE = ItemRegistry.registerTool("netherite_spade", () -> new Spade(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_DESTROYER = ItemRegistry.registerTool("netherite_destroyer", () -> new Destroyer(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> DEBUGGER = ItemRegistry.registerItem("debug_sword", Debugger::new);

        private static void init() {

        }
    }

    public static class Material {
        public static final RegistryObject<Item> NIGHTMARE_ENERGY = registerMaterial("nightmare_energy", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_IRON_INGOT = registerMaterial("reinforced_iron_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_GOLD_INGOT = registerMaterial("reinforced_gold_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> MATRIX_INGOT = registerMaterial("matrix_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM = registerMaterial("blue_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> RED_GEM = registerMaterial("red_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> YELLOW_GEM = registerMaterial("yellow_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLACK_GEM = registerMaterial("black_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> WHITE_GEM = registerMaterial("white_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> PURPLE_GEM = registerMaterial("purple_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> WATER_GEMSTONE = registerMaterial("water_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> NETHER_CRYSTAL_STONE = registerMaterial("nether_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> SANDSTORM_CRYSTAL_STONE = registerMaterial("sandstorm_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> ABYSS_CRYSTAL_STONE = registerMaterial("abyss_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> WHITE_ABYSS_CRYSTAL_STONE = registerMaterial("white_abyss_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> END_CRYSTAL_STONE = registerMaterial("end_crystal_stone", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        public static final RegistryObject<Item> BLOOD_STONE = registerMaterial("blood_stone", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> VOID_CRYSTAL = registerMaterial("void_crystal", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> CONDENSED_CRYSTAL = registerMaterial("condensed_crystal", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));


        public static final RegistryObject<Item> APOCALYPTIC_CRYSTAL = registerMaterial("apocalyptic_crystal", () -> new Item(new Item.Properties()));

        private static void init() {
        }
    }

    public static class BlockItem {
        public static final RegistryObject<Item> DUNGEON_TORCH = ItemRegistry.registerBlockItem("dungeon_torch", () -> new StandingAndWallBlockItem(OHBlocks.Functional.DUNGEON_TORCH.get(), OHBlocks.Functional.WALL_DUNGEON_TORCH.get(), new Item.Properties(), Direction.DOWN));

        private static void init() {
        }
    }

    public static class Armor {
        public static final RegistryObject<Item> BLUE_GEM_HELMET = ItemRegistry.registerArmor("blue_gem_helmet", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.HELMET, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_CHESTPLATE = ItemRegistry.registerArmor("blue_gem_chestplate", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_LEGGINGS = ItemRegistry.registerArmor("blue_gem_leggings", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_BOOTS = ItemRegistry.registerArmor("blue_gem_boots", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.BOOTS, new Item.Properties()));

        public static final RegistryObject<Item> BLOOD_STONE_HELMET = ItemRegistry.registerArmor("blood_stone_helmet", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.HELMET, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_CHESTPLATE = ItemRegistry.registerArmor("blood_stone_chestplate", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_LEGGINGS = ItemRegistry.registerArmor("blood_stone_leggings", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_BOOTS = ItemRegistry.registerArmor("blood_stone_boots", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.BOOTS, new Item.Properties()));

        private static void init() {
        }
    }

    public static class Utilities {
        public static final RegistryObject<Item> BEAR = ItemRegistry.registerUtilities("bear", () -> new Item(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> BLOOD_BEAR = ItemRegistry.registerUtilities("blood_bear", () -> new Item(new Item.Properties().stacksTo(1)) {
            @Nonnull
            @Override
            public InteractionResult useOn(@Nonnull UseOnContext context) {
                Player player = context.getPlayer();
                if (player == null) {
                    return super.useOn(context);
                }
                if (player.level().getBlockState(context.getClickedPos()).is(OHBlocks.Functional.TEXTURES_TEST_BLOCK.get())) {
                    if (EntityUtils.isInDimension(player, OHDimensions.NIGHTMARE)) {
                        if (context.getItemInHand().is(Utilities.BLOOD_BEAR.get())) {
                            if (player instanceof ServerPlayer serverPlayer) {
                                EntityUtils.travelToDimension(serverPlayer, OHDimensions.DYSTOPIA);
                                ServerLevel nextLevel = serverPlayer.server.getLevel(OHDimensions.DYSTOPIA);
                                if (nextLevel != null) {
                                    EntityUtils.spreadEntity(nextLevel, serverPlayer, new Vec2(0, 0), 128, 192, 128);
                                }
                            }
                        }
                    }
                }
                return super.useOn(context);
            }
        });
        public static final RegistryObject<Item> DAMOCLES_SWORD = ItemRegistry.registerUtilities("damocles_sword", DamoclesSword::new);

        private static void init() {
        }
    }

    public static class Weapon {
        public static final RegistryObject<Item> ZENITH_CRIMSON_DEMISE = ItemRegistry.registerWeaponMelee("zenith_crimson_demise", ZenithCrimsonDemise::new);

        private static void init() {
            Melee.init();
            Tank.init();
            Ranged.init();
            Magic.init();
        }

        public static class Ranged {
            private static void init() {
                Gun.init();
            }

            public static class Gun {
                public static final RegistryObject<Item> MALICIOUS = ItemRegistry.registerWeaponRanged("malicious", Malicious::new);
                public static final RegistryObject<Item> FREQUENCY_VARIATION = ItemRegistry.registerWeaponRanged("frequency_variation", FrequencyVariation::new);
                public static final RegistryObject<Item> PAO = ItemRegistry.registerWeaponRanged("genocide", Genocide::new);
                public static final RegistryObject<Item> ANCESTORS_SHOOTER = ItemRegistry.registerWeaponRanged("ancestors_shooter", AncestorsShooter::new);
                public static final RegistryObject<Item> VOID_IMPACT = ItemRegistry.registerWeaponRanged("void_impact", VoidImpact::new);
                public static final RegistryObject<Item> CARAMEL_BAKA = ItemRegistry.registerWeaponRanged("caramel_baka", CaramelBaka::new);

                private static void init() {
                }
            }

        }

        public static class Magic {
            private static void init() {
                Staff.init();
                Book.init();
            }

            public static class Staff {

                public static final RegistryObject<Item> FIRE_WAND = ItemRegistry.registerWeaponMagic("fire_wand", FireWand::new);

                private static void init() {
                }
            }

            public static class Book {

                public static final RegistryObject<Item> APPRENTICE_SHIELD_BOOK = ItemRegistry.registerWeaponMagic("apprentice_shield_book", ShieldBook::new);

                private static void init() {

                }
            }

        }

        public static class Tank {
            private static void init() {
                Shield.init();
            }

            public static class Shield {
                public static class Buckler {
                    public static final RegistryObject<Item> BLOOD_STONE_BUCKLER = ItemRegistry.registerWeaponTank("blood_stone_buckler", () -> new com.arc.outland_horizon.world.item.weapons.tank.buckler.Buckler(new Item.Properties(), 40, 0.8f, 100));

                    private static void init() {
                    }
                }

                private static void init() {
                    Buckler.init();
                }
            }

        }

        public static class Melee {
            private static void init() {
                Sword.init();
            }

            public static class Sword {
                public static final RegistryObject<Item> BLAZE_SWORD = ItemRegistry.registerWeaponMelee("blaze_sword", BlazeSword::new);
                public static final RegistryObject<Item> BLUE_GEM_SWORD = ItemRegistry.registerWeaponMelee("blue_gem_sword", () -> new SwordItem(ModTiers.BLUE_GEM, 3, -2.4f, new Item.Properties()));
                public static final RegistryObject<Item> BLOOD_STONE_SWORD = ItemRegistry.registerWeaponMelee("blood_stone_sword", () -> new SwordItem(ModTiers.BLOOD_STONE, 3, -2.4f, new Item.Properties()));
                public static final RegistryObject<Item> MATRIX_SWORD = ItemRegistry.registerWeaponMelee("matrix_sword", () -> new SwordItem(ModTiers.MATRIX_INGOT, 3, -2.4f, new Item.Properties().rarity(Rarity.UNCOMMON)));
                public static final RegistryObject<Item> ELEGY = ItemRegistry.registerWeaponMelee("elegy", Elegy::new);

                private static void init() {
                }
            }
        }


    }

    public static class Ornament {
        public static final RegistryObject<Item> ZOMBIE_MEDAL_COPPER = ItemRegistry.registerOrnament("zombie_medal_copper", ZombieMedal.Copper::new);
        public static final RegistryObject<Item> ZOMBIE_MEDAL_SILVER = ItemRegistry.registerOrnament("zombie_medal_silver", ZombieMedal.Silver::new);
        public static final RegistryObject<Item> ZOMBIE_MEDAL_GOLD = ItemRegistry.registerOrnament("zombie_medal_gold", ZombieMedal.Gold::new);
        public static final RegistryObject<Item> BRIGHT_TALISMAN = ItemRegistry.registerOrnament("bright_talisman", Bright::new);

        private static void init() {
        }
    }
}
