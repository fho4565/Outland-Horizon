package com.isl.outland_horizon.client.generators;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class SingleRecipe {
    RecipeCategory category;
    public String[] patterns;
    public HashMap<Character, Item> keys;
    public CriterionTriggerInstance criteria;
    public ItemStack result;

    public SingleRecipe(RecipeCategory category, String[] patterns, HashMap<Character, Item> keys, CriterionTriggerInstance criteria, ItemStack result) {
        this.category = category;
        this.patterns = patterns;
        this.keys = keys;
        this.criteria = criteria;
        this.result = result;
    }
}
