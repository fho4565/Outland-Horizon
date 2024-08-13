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
            return 15;
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
}
