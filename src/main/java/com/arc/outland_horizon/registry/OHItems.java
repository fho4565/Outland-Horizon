package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.ModArmorMaterials;
import com.arc.outland_horizon.ModTiers;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.item.consumables.potions.MadPotion;
import com.arc.outland_horizon.world.item.consumables.potions.ManaPotion;
import com.arc.outland_horizon.world.item.medal.ZombieMedal;
import com.arc.outland_horizon.world.item.tools.DungeonDestroyer;
import com.arc.outland_horizon.world.item.tools.ZeroReformer;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import com.arc.outland_horizon.world.item.weapons.magic.book.ShieldBook;
import com.arc.outland_horizon.world.item.weapons.magic.wand.FireWand;
import com.arc.outland_horizon.world.item.weapons.melee.sickle.ZenithCrimsonDemise;
import com.arc.outland_horizon.world.item.weapons.melee.sword.BlazeSword;
import com.arc.outland_horizon.world.item.weapons.melee.sword.Debugger;
import com.arc.outland_horizon.world.item.weapons.melee.sword.Elegy;
import com.arc.outland_horizon.world.item.weapons.ranged.gun.*;
import com.arc.outland_horizon.world.item.weapons.tank.buckler.Buckler;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class OHItems {
    public static final RegistryObject<Item> ZOMBIE_MEDAL_COPPER = ItemRegistry.registerTool("zombie_medal_copper", ZombieMedal.Copper::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_SILVER = ItemRegistry.registerTool("zombie_medal_silver", ZombieMedal.Silver::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_GOLD = ItemRegistry.registerTool("zombie_medal_gold", ZombieMedal.Gold::new);

    public static void init() {
        Material.init();
        Consumable.init();
        Tool.init();
        Weapon.init();
        Armor.init();
        BlockItem.init();
        Utilities.init();
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
        public static final RegistryObject<Item> BLOOD_BUCKET = ItemRegistry.registerTool("blood_bucket", () -> new BucketItem(OHBlocks.Fluid.FluidRegistry.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        public static final RegistryObject<Item> ZERO_REFORMER = ItemRegistry.registerTool("zero_reformer", ZeroReformer::new);
        public static final RegistryObject<Item> DUNGEON_DESTROYER = ItemRegistry.registerTool("dungeon_destroyer", DungeonDestroyer::new);

        public static final RegistryObject<Item> BLUE_GEM_PICKAXE = ItemRegistry.registerTool("blue_gem_pickaxe", () -> new PickaxeItem(ModTiers.BLUE_GEM, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_AXE = ItemRegistry.registerTool("blue_gem_axe", () -> new AxeItem(ModTiers.BLUE_GEM, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SHOVEL = ItemRegistry.registerTool("blue_gem_shovel", () -> new ShovelItem(ModTiers.BLUE_GEM, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HOE = ItemRegistry.registerTool("blue_gem_hoe", () -> new HoeItem(ModTiers.BLUE_GEM, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_PAXEL = ItemRegistry.registerTool("blue_gem_paxel", () -> new Paxel(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HAMMER = ItemRegistry.registerTool("blue_gem_hammer", () -> new Hammer(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SPADE = ItemRegistry.registerTool("blue_gem_spade", () -> new Spade(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_DESTROYER = ItemRegistry.registerTool("blue_gem_destroyer", () -> new Destroyer(ModTiers.BLUE_GEM, new Item.Properties()));

        public static final RegistryObject<Item> BLOOD_STONE_PICKAXE = ItemRegistry.registerTool("blood_stone_pickaxe", () -> new PickaxeItem(ModTiers.BLOOD_STONE, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_AXE = ItemRegistry.registerTool("blood_stone_axe", () -> new AxeItem(ModTiers.BLOOD_STONE, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SHOVEL = ItemRegistry.registerTool("blood_stone_shovel", () -> new ShovelItem(ModTiers.BLOOD_STONE, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HOE = ItemRegistry.registerTool("blood_stone_hoe", () -> new HoeItem(ModTiers.BLOOD_STONE, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_PAXEL = ItemRegistry.registerTool("blood_stone_paxel", () -> new Paxel(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HAMMER = ItemRegistry.registerTool("blood_stone_hammer", () -> new Hammer(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SPADE = ItemRegistry.registerTool("blood_stone_spade", () -> new Spade(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_DESTROYER = ItemRegistry.registerTool("blood_stone_destroyer", () -> new Destroyer(ModTiers.BLOOD_STONE, new Item.Properties()));

        public static final RegistryObject<Item> WOODEN_PAXEL = ItemRegistry.registerTool("wooden_paxel", () -> new Paxel(new Tier() {
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

        public static final RegistryObject<Item> STONE_PAXEL = ItemRegistry.registerTool("stone_paxel", () -> new Paxel(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_HAMMER = ItemRegistry.registerTool("stone_hammer", () -> new Hammer(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_SPADE = ItemRegistry.registerTool("stone_spade", () -> new Spade(Tiers.STONE, new Item.Properties()));
        public static final RegistryObject<Item> STONE_DESTROYER = ItemRegistry.registerTool("stone_destroyer", () -> new Destroyer(Tiers.STONE, new Item.Properties()));

        public static final RegistryObject<Item> IRON_PAXEL = ItemRegistry.registerTool("iron_paxel", () -> new Paxel(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_HAMMER = ItemRegistry.registerTool("iron_hammer", () -> new Hammer(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_SPADE = ItemRegistry.registerTool("iron_spade", () -> new Spade(Tiers.IRON, new Item.Properties()));
        public static final RegistryObject<Item> IRON_DESTROYER = ItemRegistry.registerTool("iron_destroyer", () -> new Destroyer(Tiers.IRON, new Item.Properties()));

        public static final RegistryObject<Item> GOLDEN_PAXEL = ItemRegistry.registerTool("golden_paxel", () -> new Paxel(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_HAMMER = ItemRegistry.registerTool("golden_hammer", () -> new Hammer(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_SPADE = ItemRegistry.registerTool("golden_spade", () -> new Spade(Tiers.GOLD, new Item.Properties()));
        public static final RegistryObject<Item> GOLDEN_DESTROYER = ItemRegistry.registerTool("golden_destroyer", () -> new Destroyer(Tiers.GOLD, new Item.Properties()));

        public static final RegistryObject<Item> DIAMOND_PAXEL = ItemRegistry.registerTool("diamond_paxel", () -> new Paxel(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_HAMMER = ItemRegistry.registerTool("diamond_hammer", () -> new Hammer(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_SPADE = ItemRegistry.registerTool("diamond_spade", () -> new Spade(Tiers.DIAMOND, new Item.Properties()));
        public static final RegistryObject<Item> DIAMOND_DESTROYER = ItemRegistry.registerTool("diamond_destroyer", () -> new Destroyer(Tiers.DIAMOND, new Item.Properties()));

        public static final RegistryObject<Item> NETHERITE_PAXEL = ItemRegistry.registerTool("netherite_paxel", () -> new Paxel(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_HAMMER = ItemRegistry.registerTool("netherite_hammer", () -> new Hammer(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_SPADE = ItemRegistry.registerTool("netherite_spade", () -> new Spade(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> NETHERITE_DESTROYER = ItemRegistry.registerTool("netherite_destroyer", () -> new Destroyer(Tiers.NETHERITE, new Item.Properties()));
        public static final RegistryObject<Item> DEBUGGER = ItemRegistry.registerItem("debug_sword", Debugger::new);

        private static void init() {
        }
    }

    public static class Material {
        public static final RegistryObject<Item> NIGHTMARE_ENERGY = ItemRegistry.registerMaterial("nightmare_energy", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_IRON_INGOT = ItemRegistry.registerMaterial("reinforced_iron_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_GOLD_INGOT = ItemRegistry.registerMaterial("reinforced_gold_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> MATRIX_INGOT = ItemRegistry.registerMaterial("matrix_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM = ItemRegistry.registerMaterial("blue_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE = ItemRegistry.registerMaterial("blood_stone", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> VOID_CRYSTAL = ItemRegistry.registerMaterial("void_crystal", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> CONDENSED_CRYSTAL = ItemRegistry.registerMaterial("condensed_crystal", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));


        public static final RegistryObject<Item> APOCALYPTIC_CRYSTAL = ItemRegistry.registerMaterial("apocalyptic_crystal", () -> new Item(new Item.Properties()));

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
        public static final RegistryObject<Item> BLOOD_BEAR = ItemRegistry.registerUtilities("blood_bear", () -> new Item(new Item.Properties().stacksTo(1)));

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
            public static void init() {
                Gun.init();
            }

            public static class Gun {
                public static final RegistryObject<Item> MALICIOUS = ItemRegistry.registerWeaponRanged("malicious", Malicious::new);
                public static final RegistryObject<Item> FREQUENCY_VARIATION = ItemRegistry.registerWeaponRanged("frequency_variation", FrequencyVariation::new);
                public static final RegistryObject<Item> PAO = ItemRegistry.registerWeaponRanged("genocide", Genocide::new);
                public static final RegistryObject<Item> ANCESTORS_SHOOTER = ItemRegistry.registerWeaponRanged("ancestors_shooter", AncestorsShooter::new);
                public static final RegistryObject<Item> VOID_IMPACT = ItemRegistry.registerWeaponRanged("void_impact", VoidImpact::new);
                public static final RegistryObject<Item> CARAMEL_BAKA = ItemRegistry.registerWeaponRanged("caramel_baka", CaramelBaka::new);

                public static void init() {
                }
            }

        }

        public static class Magic {
            public static void init() {
                Staff.init();
                Book.init();
            }

            public static class Staff {

                public static final RegistryObject<Item> FIRE_WAND = ItemRegistry.registerWeaponMagic("fire_wand", FireWand::new);

                public static void init() {
                }
            }

            public static class Book {

                public static final RegistryObject<Item> APPRENTICE_SHIELD_BOOK = ItemRegistry.registerWeaponMagic("apprentice_shield_book", ShieldBook::new);

                public static void init() {

                }
            }

        }

        public static class Tank {
            public static void init() {
                Shield.init();
            }

            public static class Shield {
                public static final RegistryObject<Item> blood_stone_buckler = ItemRegistry.registerWeaponMelee("blood_stone_buckler", () -> new Buckler(new Item.Properties(), 40, 0.8f, 100));

                public static void init() {
                }
            }

        }

        public static class Melee {
            public static void init() {
                Sword.init();
            }

            public static class Sword {
                public static final RegistryObject<Item> BLAZE_SWORD = ItemRegistry.registerWeaponMelee("blaze_sword", BlazeSword::new);
                public static final RegistryObject<Item> BLUE_GEM_SWORD = ItemRegistry.registerWeaponMelee("blue_gem_sword", () -> new SwordItem(ModTiers.BLUE_GEM, 3, -2.4f, new Item.Properties()));
                public static final RegistryObject<Item> BLOOD_STONE_SWORD = ItemRegistry.registerWeaponMelee("blood_stone_sword", () -> new SwordItem(ModTiers.BLOOD_STONE, 3, -2.4f, new Item.Properties()));
                public static final RegistryObject<Item> MATRIX_SWORD = ItemRegistry.registerWeaponMelee("matrix_sword", () -> new SwordItem(ModTiers.MATRIX_INGOT, 9, -2.4f, new Item.Properties()));
                public static final RegistryObject<Item> ELEGY = ItemRegistry.registerWeaponMelee("elegy", Elegy::new);

                public static void init() {
                }
            }
        }


    }
}
