package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class ModRecipe extends RecipeProvider {
    public ModRecipe(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        tools(writer, OHItems.Material.BLOOD_STONE.get(),
                OHItems.Weapon.Melee.Sword.BLOOD_STONE_SWORD.get(),
                OHItems.Tool.BLOOD_STONE_PICKAXE.get(),
                OHItems.Tool.BLOOD_STONE_AXE.get(),
                OHItems.Tool.BLOOD_STONE_SHOVEL.get(),
                OHItems.Tool.BLOOD_STONE_HOE.get(),
                OHItems.Tool.BLOOD_STONE_PAXEL.get(),
                OHItems.Tool.BLOOD_STONE_SPADE.get(),
                OHItems.Tool.BLOOD_STONE_HAMMER.get(),
                OHItems.Tool.BLOOD_STONE_DESTROYER.get());
    }

    private void tools(Consumer<FinishedRecipe> writer, Item material, Item sword, Item pickaxe, Item axe, Item shovel, Item hoe, Item paxel, Item spade, Item hammer, Item destroyer) {
        sword(writer, material, sword);
        pickaxe(writer, material, pickaxe);
        axe(writer, material, axe);
        shovel(writer, material, shovel);
        hoe(writer, material, hoe);
        paxel(writer, axe, pickaxe, shovel, paxel);
        spade(writer, material, spade);
        hammer(writer, material, hammer);
        destroyer(writer, spade, hammer, destroyer);
    }

    private void sword(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("a")
                .pattern("a")
                .pattern("l")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void pickaxe(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("aaa")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void axe(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("aa")
                .pattern("al")
                .pattern(" l")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void shovel(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("a")
                .pattern("l")
                .pattern("l")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void hoe(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("aa")
                .pattern(" l")
                .pattern(" l")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void paxel(Consumer<FinishedRecipe> writer, Item axe, Item pickaxe, Item shovel, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("abc")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', axe)
                .define('b', pickaxe)
                .define('c', shovel)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(axe, pickaxe, shovel))
                .save(writer);
    }

    private void spade(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("aaa")
                .pattern("aaa")
                .pattern(" l ")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void hammer(Consumer<FinishedRecipe> writer, Item material, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("aaa")
                .pattern("ala")
                .pattern(" l ")
                .define('a', material)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void destroyer(Consumer<FinishedRecipe> writer, Item spade, Item hammer, Item output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("alb")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', spade)
                .define('b', hammer)
                .define('l', Items.STICK)
                .unlockedBy("criteria", hasItems(spade, hammer))
                .save(writer);
    }
}
