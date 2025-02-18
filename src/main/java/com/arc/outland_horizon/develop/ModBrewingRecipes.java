package com.arc.outland_horizon.develop;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

import java.util.ArrayList;
import java.util.List;

public class ModBrewingRecipes {
    static final ArrayList<BrewingRecipe> recipes = new ArrayList<>();

    public static List<BrewingRecipe> getAllBrewingRecipes() {
        return recipes;
    }

    public static class Builder {
        private Ingredient bottle = Ingredient.of(Items.POTION);
        private Ingredient ingredient = Ingredient.of(Items.NETHER_WART);
        private ItemStack potion = Items.POTION.getDefaultInstance();

        private Builder() {
        }

        public static Builder of() {
            return new Builder();
        }

        /**
         * @param bottleItem 与水瓶放在相同插槽中的成分。
         */
        public Builder bottle(Ingredient bottleItem) {
            this.bottle = bottleItem;
            return this;
        }

        /**
         * @param ingredient 与下界疣位于同一插槽中的成分。
         */
        public Builder ingredient(Ingredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        /**
         * @param potion 酿造完成的物品
         */
        public Builder potion(ItemStack potion) {
            this.potion = potion;
            return this;
        }

        public void build() {
            recipes.add(new BrewingRecipe(bottle, ingredient, potion));
        }
    }

    public static void generate() {

    }
}
