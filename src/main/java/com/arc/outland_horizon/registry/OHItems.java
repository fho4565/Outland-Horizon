package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.ModArmorMaterials;
import com.arc.outland_horizon.ModTiers;
import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.item.consumables.potions.MadPotion;
import com.arc.outland_horizon.world.item.consumables.potions.ManaPotion;
import com.arc.outland_horizon.world.item.medal.ZombieMedal;
import com.arc.outland_horizon.world.item.tools.ZeroReformer;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.book.ShieldBook;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.wand.FireWand;
import com.arc.outland_horizon.world.item.weapons.weapon.melee.AAASword;
import com.arc.outland_horizon.world.item.weapons.weapon.melee.BlazeSword;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun.*;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OHItems {
    public static final RegistryObject<Item> ZOMBIE_MEDAL_COPPER = registerTool("zombie_medal_copper", ZombieMedal.Copper::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_SILVER = registerTool("zombie_medal_silver", ZombieMedal.Silver::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_GOLD = registerTool("zombie_medal_gold", ZombieMedal.Gold::new);

    private static RegistryObject<Item> registerWeaponRanged(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.RANGED_WEAPON_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerConsumables(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.CONSUMABLES_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerMaterial(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MATERIAL_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerTool(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.TOOL_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerWeaponMagic(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MAGIC_WEAPON_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerWeaponMelee(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MELEE_WEAPON_LIST.add(object);
        return object;
    }

    private static RegistryObject<Item> registerArmor(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.ARMOR_LIST.add(object);
        return object;
    }

    public static RegistryObject<Item> registerBlockItem(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.BLOCK_ITEM_LIST.add(object);
        return object;
    }

    public static void init() {
        Material.init();
        Consumable.init();
        Tool.init();
        Weapon.init();
        Armor.init();
        BlockItem.init();
        Tier nTier = new Tier() {
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
        };
        registerTool("wooden_paxel", () -> new Paxel(nTier, new Item.Properties()));
        initVanillaExtends("golden", Tiers.GOLD);
        initVanillaExtends("stone", Tiers.STONE);
        initVanillaExtends("iron", Tiers.IRON);
        initVanillaExtends("diamond", Tiers.DIAMOND);
        initVanillaExtends("netherite", Tiers.NETHERITE);
    }

    private static void initVanillaExtends(String nameWithPrefix, Tier tier) {
        Tier nTier = new Tier() {
            @Override
            public int getUses() {
                return Math.round(tier.getUses() * 2.5f);
            }

            @Override
            public float getSpeed() {
                return tier.getSpeed() * 0.9f;
            }

            @Override
            public float getAttackDamageBonus() {
                return tier.getAttackDamageBonus();
            }

            @Override
            public int getLevel() {
                return tier.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return tier.getEnchantmentValue();
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return tier.getRepairIngredient();
            }
        };
        registerTool(nameWithPrefix + "_paxel", () -> new Paxel(nTier, new Item.Properties()));
        registerTool(nameWithPrefix + "_hammer", () -> new Hammer(tier, new Item.Properties()));
        registerTool(nameWithPrefix + "_spade", () -> new Spade(tier, new Item.Properties()));
        registerTool(nameWithPrefix + "_destroyer", () -> new Destroyer(nTier, new Item.Properties()));
    }

    public static class Consumable {
        public static final RegistryObject<Item> MANA_POTION_COMMON = registerConsumables("mana_potion.common", ManaPotion.Common::new);
        public static final RegistryObject<Item> MANA_POTION_INTERMEDIATE = registerConsumables("mana_potion.intermediate", ManaPotion.Intermediate::new);
        public static final RegistryObject<Item> MANA_POTION_ADVANCED = registerConsumables("mana_potion.advanced", ManaPotion.Advanced::new);
        public static final RegistryObject<Item> MANA_POTION_SUPER = registerConsumables("mana_potion.super", ManaPotion.Super::new);
        public static final RegistryObject<Item> MANA_POTION_ULTIMATE = registerConsumables("mana_potion.ultimate", ManaPotion.Ultimate::new);
        public static final RegistryObject<Item> RAGE_POTION = registerConsumables("mad_potion", MadPotion::new);
        public static final RegistryObject<Item> PlatterOfMeatsRaw = registerConsumables("platter_of_raw_meats", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().meat().nutrition(10).saturationMod(6.0f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, Utils.secondsToTicks(10)), 0.6f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, Utils.secondsToTicks(30), 1), 1.0f).build())));
        public static final RegistryObject<Item> PlatterOfMeatsCooked = registerConsumables("platter_of_cooked_meats", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .meat()
                .nutrition(28).saturationMod(42.4f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Utils.secondsToTicks(30)), 1.0f)
                .build())));

        private static void init() {
        }
    }

    public static class Tool {
        public static final RegistryObject<Item> BLOOD_BUCKET = registerTool("blood_bucket", () -> new BucketItem(OHBlocks.Fluid.FluidRegistry.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        public static final RegistryObject<Item> ZERO_REFORMER = registerTool("zero_reformer", ZeroReformer::new);

        public static final RegistryObject<Item> BLUE_GEM_PICKAXE = registerTool("blue_gem_pickaxe", () -> new PickaxeItem(ModTiers.BLUE_GEM, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_AXE = registerTool("blue_gem_axe", () -> new AxeItem(ModTiers.BLUE_GEM, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SHOVEL = registerTool("blue_gem_shovel", () -> new ShovelItem(ModTiers.BLUE_GEM, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HOE = registerTool("blue_gem_hoe", () -> new HoeItem(ModTiers.BLUE_GEM, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_PAXEL = registerTool("blue_gem_paxel", () -> new Paxel(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_HAMMER = registerTool("blue_gem_hammer", () -> new Hammer(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_SPADE = registerTool("blue_gem_spade", () -> new Spade(ModTiers.BLUE_GEM, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_DESTROYER = registerTool("blue_gem_destroyer", () -> new Destroyer(ModTiers.BLUE_GEM, new Item.Properties()));


        public static final RegistryObject<Item> BLOOD_STONE_PICKAXE = registerTool("blood_stone_pickaxe", () -> new PickaxeItem(ModTiers.BLOOD_STONE, 1, -2.8f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_AXE = registerTool("blood_stone_axe", () -> new AxeItem(ModTiers.BLOOD_STONE, 6, -3.1f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SHOVEL = registerTool("blood_stone_shovel", () -> new ShovelItem(ModTiers.BLOOD_STONE, 1.5f, -3.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HOE = registerTool("blood_stone_hoe", () -> new HoeItem(ModTiers.BLOOD_STONE, -2, -1.0f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_PAXEL = registerTool("blood_stone_paxel", () -> new Paxel(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_HAMMER = registerTool("blood_stone_hammer", () -> new Hammer(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SPADE = registerTool("blood_stone_spade", () -> new Spade(ModTiers.BLOOD_STONE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_DESTROYER = registerTool("blood_stone_destroyer", () -> new Destroyer(ModTiers.BLOOD_STONE, new Item.Properties()));

        private static void init() {
        }
    }

    public static class Material {
        public static final RegistryObject<Item> NIGHTMARE_ENERGY = registerMaterial("nightmare_energy", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_IRON_INGOT = registerMaterial("reinforced_iron_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> REINFORCED_GOLD_INGOT = registerMaterial("reinforced_gold_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> MATRIX_INGOT = registerMaterial("matrix_ingot", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM = registerMaterial("blue_gem", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE = registerMaterial("blood_stone", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> VOID_CRYSTAL = registerMaterial("void_crystal", () -> new Item(new Item.Properties()));

        private static void init() {
        }
    }

    public static class BlockItem {
        public static final RegistryObject<Item> DUNGEON_TORCH = registerBlockItem("dungeon_torch", () -> new StandingAndWallBlockItem(OHBlocks.Functional.DUNGEON_TORCH.get(), OHBlocks.Functional.WALL_DUNGEON_TORCH.get(), new Item.Properties(), Direction.DOWN));

        private static void init() {
        }
    }

    public static class Armor {
        public static final RegistryObject<Item> BLUE_GEM_HELMET = registerArmor("blue_gem_helmet", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.HELMET, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_CHESTPLATE = registerArmor("blue_gem_chestplate", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_LEGGINGS = registerArmor("blue_gem_leggings", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        public static final RegistryObject<Item> BLUE_GEM_BOOTS = registerArmor("blue_gem_boots", () -> new ArmorItem(ModArmorMaterials.BLUE_GEM, ArmorItem.Type.BOOTS, new Item.Properties()));

        public static final RegistryObject<Item> BLOOD_STONE_HELMET = registerArmor("blood_stone_helmet", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.HELMET, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_CHESTPLATE = registerArmor("blood_stone_chestplate", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_LEGGINGS = registerArmor("blood_stone_leggings", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_BOOTS = registerArmor("blood_stone_boots", () -> new ArmorItem(ModArmorMaterials.BLOOD_STONE, ArmorItem.Type.BOOTS, new Item.Properties()));

        private static void init() {
        }
    }

    public static class Weapon {
        public static final RegistryObject<Item> BLAZE_SWORD = registerWeaponMelee("blaze_sword", BlazeSword::new);
        public static final RegistryObject<Item> DEBUG_SWORD = registerWeaponMelee("debug_sword", AAASword::new);
        public static final RegistryObject<Item> BLUE_GEM_SWORD = registerWeaponMelee("blue_gem_sword", () -> new SwordItem(ModTiers.BLUE_GEM, 3, -2.4f, new Item.Properties()));
        public static final RegistryObject<Item> BLOOD_STONE_SWORD = registerWeaponMelee("blood_stone_sword", () -> new SwordItem(ModTiers.BLOOD_STONE, 3, -2.4f, new Item.Properties()));

        public static final RegistryObject<Item> MALICIOUS = registerWeaponRanged("malicious", () -> new Malicious(450, 10, Material.NIGHTMARE_ENERGY.get()));
        public static final RegistryObject<Item> FREQUENCY_VARIATION = registerWeaponRanged("frequency_variation", () -> new FrequencyVariation(375, 10, Items.DIAMOND));
        public static final RegistryObject<Item> PAO = registerWeaponRanged("genocide", () -> new Genocide(100, 10, Items.DIAMOND));
        public static final RegistryObject<Item> VOID_IMPACT = registerWeaponRanged("void_impact", () -> new VoidImpact(3500, 10, Items.DIAMOND));
        public static final RegistryObject<Item> CARAMEL_BAKA = registerWeaponRanged("caramel_baka", CaramelBaka::new);

        public static final RegistryObject<Item> FIRE_WAND = registerWeaponMagic("fire_wand", FireWand::new);
        public static final RegistryObject<Item> APPRENTICE_SHIELD_BOOK = registerWeaponMagic("apprentice_shield_book", ShieldBook::new);

        private static void init() {
        }
    }
}
