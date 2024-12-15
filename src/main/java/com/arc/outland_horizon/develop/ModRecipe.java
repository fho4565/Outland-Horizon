package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHBlockFamily;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
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
        tools(writer, OHItems.Material.BLUE_GEM.get(),
                OHItems.Weapon.Melee.Sword.BLUE_GEM_SWORD.get(),
                OHItems.Tool.BLUE_GEM_PICKAXE.get(),
                OHItems.Tool.BLUE_GEM_AXE.get(),
                OHItems.Tool.BLUE_GEM_SHOVEL.get(),
                OHItems.Tool.BLUE_GEM_HOE.get(),
                OHItems.Tool.BLUE_GEM_PAXEL.get(),
                OHItems.Tool.BLUE_GEM_SPADE.get(),
                OHItems.Tool.BLUE_GEM_HAMMER.get(),
                OHItems.Tool.BLUE_GEM_DESTROYER.get());
        woodThings(writer, OHBlocks.Building.NIGHTMARE.NIGHTMARE_LOG.get().asItem(), OHBlocks.Building.NIGHTMARE.NIGHTMARE_WOOD.get().asItem(), OHBlockFamily.NIGHTMARE_LOG);
        woodThings(writer, OHBlockFamily.COAGULATED_NIGHTMARE_LOG);
    }

    private void logSplit(Consumer<FinishedRecipe> writer, Item log, Item wood, Item planks) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(log)
                .unlockedBy("criteria", hasItems(log))
                .save(writer, OutlandHorizon.createModResourceLocation(Utils.getDescriptionIdName(log) + "_to_" + Utils.getDescriptionIdName(planks)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(wood)
                .unlockedBy("criteria", hasItems(wood))
                .save(writer, OutlandHorizon.createModResourceLocation(Utils.getDescriptionIdName(wood) + "_to_" + Utils.getDescriptionIdName(planks)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 3)
                .pattern("aa")
                .pattern("aa").define('a', log)
                .unlockedBy("criteria", hasItems(log))
                .save(writer);
    }

    private void woodThings(Consumer<FinishedRecipe> writer, Item planks, Item pressure_plate, Item trapdoor, Item button, Item stairs, Item slab, Item fence_gate, Item fence, Item door) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button)
                .requires(planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("  a")
                .pattern(" aa")
                .pattern("aaa").define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("aaa").define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence_gate)
                .pattern("lal")
                .pattern("lal").define('l', Items.STICK).define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence)
                .pattern("ala")
                .pattern("ala").define('l', Items.STICK).define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3)
                .pattern("aa")
                .pattern("aa")
                .pattern("aa").define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, pressure_plate)
                .pattern("aa").define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy("criteria", hasItems(planks))
                .save(writer);
    }

    private void woodThings(Consumer<FinishedRecipe> writer, Item log, Item wood, Item planks, Item pressure_plate, Item trapdoor, Item button, Item stairs, Item slab, Item fence_gate, Item fence, Item door) {
        logSplit(writer, log, wood, planks);
        woodThings(writer, planks, pressure_plate, trapdoor, button, stairs, slab, fence_gate, fence, door);
    }

    private void woodThings(Consumer<FinishedRecipe> writer, BlockFamily blockFamily) {
        Item planks = blockFamily.getBaseBlock().asItem();
        Item button = blockFamily.get(BlockFamily.Variant.BUTTON).asItem();
        Item stairs = blockFamily.get(BlockFamily.Variant.STAIRS).asItem();
        Item slab = blockFamily.get(BlockFamily.Variant.SLAB).asItem();
        Item fence_gate = blockFamily.get(BlockFamily.Variant.FENCE_GATE).asItem();
        Item fence = blockFamily.get(BlockFamily.Variant.FENCE).asItem();
        Item door = blockFamily.get(BlockFamily.Variant.DOOR).asItem();
        Item pressure_plate = blockFamily.get(BlockFamily.Variant.PRESSURE_PLATE).asItem();
        Item trapdoor = blockFamily.get(BlockFamily.Variant.TRAPDOOR).asItem();
        woodThings(writer, planks, pressure_plate, trapdoor, button, stairs, slab, fence_gate, fence, door);
    }

    private void woodThings(Consumer<FinishedRecipe> writer, Item log, Item wood, BlockFamily blockFamily) {
        Item planks = blockFamily.getBaseBlock().asItem();
        Item button = blockFamily.get(BlockFamily.Variant.BUTTON).asItem();
        Item stairs = blockFamily.get(BlockFamily.Variant.STAIRS).asItem();
        Item slab = blockFamily.get(BlockFamily.Variant.SLAB).asItem();
        Item fence_gate = blockFamily.get(BlockFamily.Variant.FENCE_GATE).asItem();
        Item fence = blockFamily.get(BlockFamily.Variant.FENCE).asItem();
        Item door = blockFamily.get(BlockFamily.Variant.DOOR).asItem();
        Item pressure_plate = blockFamily.get(BlockFamily.Variant.PRESSURE_PLATE).asItem();
        Item trapdoor = blockFamily.get(BlockFamily.Variant.TRAPDOOR).asItem();
        woodThings(writer, log, wood, planks, pressure_plate, trapdoor, button, stairs, slab, fence_gate, fence, door);
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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
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
