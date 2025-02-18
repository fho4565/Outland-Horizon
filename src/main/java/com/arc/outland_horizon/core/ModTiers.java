package com.arc.outland_horizon.core;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHItems.Material;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class ModTiers {
    public static final double BLUE_GEM_LEVEL = 2;//蓝宝石，在海洋生成
    public static final double YELLOW_GEM_LEVEL = 2;//黄宝石，在地下沙漠生成
    public static final double RED_GEM_LEVEL = 2.2;//红宝石，在地狱生成
    public static final double PURPLE_GEM_LEVEL = 2.3;//紫宝石，在末地生成
    public static final double BLACK_GEM_LEVEL = 2.5;//黑宝石，在深渊生成
    public static final double WHITE_GEM_LEVEL = 2.5;//白宝石，在白渊生成
    public static final double BRILLIANT_GOLD_LEVEL = 2.5;//璀璨金，白宝石和金锭合成
    public static final double SUNLIGHT_STONE_LEVEL = 2.7;//日光石
    public static final double MOONLIGHT_STONE_LEVEL = 2.7;//月光石
    public static final double WATER_GEMSTONE_LEVEL = 3.2;//水晶石，蓝宝石的上位
    public static final double BLOOD_STONE_LEVEL = 3.6;//血石
    public static final double EVIL_STONE_LEVEL = 3.7;//邪恶石，红宝石上位
    public static final double PURPUR_STONE_LEVEL = 3.7;//紫珀石，紫宝石上位
    public static final double DOUBLE_LIVED_STONE_LEVEL = 3.7;//双生石，黑宝石和白宝石合成
    public static final double SPLENDID_COPPER_LEVEL = 3.8;//辉铜，铜与双生石合成
    public static final double VOID_WASTE_LEVEL = 4;//虚空废料
    public static final double DEPLETED_URANIUM_LEVEL = 4.1;//枯竭铀
    public static final double MITHRIL_LEVEL = 4.2;//秘银
    public static final double MATRIX_LEVEL = 4.3;//矩阵
    public static final double GAMMA_ENERGY_BATTERY_LEVEL = 4.6;//伽马能量电池
    public static final double CONDENSE_LEVEL = 5;//凝结石
    public static final double VOID_CRYSTAL_LEVEL = 6.9;//虚空晶体
    static final TagKey<Block> wood = Tags.Blocks.NEEDS_WOOD_TOOL;
    static final TagKey<Block> stone = BlockTags.NEEDS_STONE_TOOL;
    static final TagKey<Block> iron = BlockTags.NEEDS_IRON_TOOL;
    static final TagKey<Block> diamond = BlockTags.NEEDS_DIAMOND_TOOL;
    static final TagKey<Block> netherite = Tags.Blocks.NEEDS_NETHERITE_TOOL;
    static final TagKey<Block> level5 = TagKey.create(Registries.BLOCK, OutlandHorizon.createModResourceLocation("needs_level_5_tool"));
    public static final Tier BLUE_GEM = createTier(BLUE_GEM_LEVEL, Ingredient.of(Material.BLUE_GEM.get()));
    public static final Tier BLOOD_STONE = createTier(BLOOD_STONE_LEVEL, Ingredient.of(Material.BLOOD_STONE.get()));
    /*public static final Tier VOID_WASTE = createTier(VOID_WASTE_LEVEL, "void_waste");
    public static final Tier MITHRIL = createTier(MITHRIL_LEVEL, "mithril");
    public static final Tier GAMMA = createTier(GAMMA_ENERGY_BATTERY_LEVEL, "gamma_energy_battery");
    public static final Tier CONDENSE = createTier(CONDENSE_LEVEL, "condense");*/
    public static final Tier VOID_CRYSTAL = createTier(VOID_CRYSTAL_LEVEL, Ingredient.of(Material.VOID_CRYSTAL.get()));
    public static final Tier MATRIX_INGOT = createTier(MATRIX_LEVEL, Ingredient.of(Material.MATRIX_INGOT.get()));

    private static @NotNull Tier createTier(double level, Ingredient repairIngredient) {
        return new Tier() {
            @Override
            public @NotNull TagKey<Block> getTag() {
                return ModTiers.getTag((int) level);
            }

            @Override
            public int getUses() {
                return (int) (385 * level);
            }

            @Override
            public float getSpeed() {
                return Math.round(4.5 * level);
            }

            @Override
            public float getAttackDamageBonus() {
                return Math.round(level);
            }

            @Override
            public int getLevel() {
                return (int) level;
            }

            @Override
            public int getEnchantmentValue() {
                return (int) (4 * level);
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return repairIngredient;
            }
        };
    }

    private static TagKey<Block> getTag(double level) {
        if (5 <= level) {
            return level5;
        } else if (1 <= level && level < 2) {
            return stone;
        } else if (2 <= level && level < 3) {
            return iron;
        } else if (3 <= level && level < 4) {
            return diamond;
        } else if (4 <= level) {
            return netherite;
        } else {
            return wood;
        }
    }
}
