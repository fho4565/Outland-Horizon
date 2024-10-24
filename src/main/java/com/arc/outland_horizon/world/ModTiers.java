package com.arc.outland_horizon.world;

import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class ModTiers {
    public static final int BLUE_GEM_LEVEL = 2;//蓝宝石
    public static final int BLOOD_STONE_LEVEL = 3;//血石
    public static final int VOID_WASTE_LEVEL = 4;//虚空废料
    public static final int MITHRIL_LEVEL = 4;//秘银
    public static final int GAMMA_LEVEL = 4;//伽马
    public static final int CONDENSE_LEVEL = 5;//凝结石
    static TagKey<Block> wood = Tags.Blocks.NEEDS_WOOD_TOOL;
    static TagKey<Block> stone = BlockTags.NEEDS_STONE_TOOL;
    static TagKey<Block> iron = BlockTags.NEEDS_IRON_TOOL;
    static TagKey<Block> diamond = BlockTags.NEEDS_DIAMOND_TOOL;
    static TagKey<Block> netherite = Tags.Blocks.NEEDS_NETHERITE_TOOL;
    static TagKey<Block> level5 = new TagKey<>(Registries.BLOCK, Utils.createModResourceLocation("needs_level_5_tool"));
    public static final Tier BLUE_GEM = createTier(BLUE_GEM_LEVEL, "blue_gem");
    public static final Tier BLOOD_STONE = createTier(BLOOD_STONE_LEVEL, "blood_stone");

    public static @NotNull Tier createTier(float level, String name) {
        return new Tier() {
            @Override
            public @NotNull TagKey<Block> getTag() {
                return ModTiers.getTag((int)level);
            }

            @Override
            public int getUses() {
                return (int) (450 * level);
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
                return (int) (7 * level);
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createModResourceLocation(name)));
            }
        };
    }
    private static TagKey<Block> getTag(int level){
        return switch (level) {
            case 1 -> stone;
            case 2 -> iron;
            case 3 -> diamond;
            case 4 -> netherite;
            case 5 -> level5;
            default -> wood;
        };
    }
}
