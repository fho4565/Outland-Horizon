package com.isl.outland_horizon.client.generators;

import com.isl.outland_horizon.level.register.ItemRegistry;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    static ArrayList<SingleRecipe> recipes = new ArrayList<>();

    public RecipeGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    public static void addRecipe(SingleRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        String name = "type";
        RecipeGenerator.addRecipe(new SingleRecipe(RecipeCategory.MISC,new String[]{"XXX","XXX","XXX"},
                new HashMap<>(){{put('X', ItemRegistry.ITEM_LIST.get(name+"_ingot").get());}},
                InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ITEM_LIST.get(name+"_ingot").get())
                ,new ItemStack(ItemRegistry.ITEM_LIST.get(name+"_block").get())));
        recipes.forEach(recipe -> {
            ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(recipe.category, recipe.result.getItem(), recipe.result.getCount())
                    .pattern(recipe.patterns[1]).pattern(recipe.patterns[2]).pattern(recipe.patterns[3])
                    .unlockedBy("criteria", recipe.criteria);
            recipe.keys.forEach(builder::define);
            builder.save(finishedRecipeConsumer);
        });
    }
}
