package com.isl.outland_horizon.world;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class Tiers {
    public static final Tier BLUE_GEM = new Tier() {
        @Override
        public int getUses() {
            return 375;
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
        public Ingredient getRepairIngredient() {
            return null;
        }
    };
}
