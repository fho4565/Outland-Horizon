package com.isl.outland_horizon.world;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ModTiers {
    public static final Tier BLUE_GEM = new Tier() {
        @Override
        public int getUses() {
            return 275;
        }
        @Override
        public float getSpeed() {
            return 6.3f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.0f;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 17;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation("blue_gem")).get());
        }
    };
    public static final Tier BLOOD_STONE = new Tier() {
        @Override
        public int getUses() {
            return 1750;
        }
        @Override
        public float getSpeed() {
            return 7.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 4.0f;
        }

        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation("blood_stone")).get());
        }
    };

    public static @NotNull Tier createTier(float level, String name) {
        return new Tier() {
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
                return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation(name)).get());
            }
        };
    }
}
