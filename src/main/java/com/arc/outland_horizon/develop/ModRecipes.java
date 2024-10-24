package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.utils.ToolPack;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.google.gson.GsonBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.arc.outland_horizon.registry.item.ItemRegistry.getItemRegistered;
import static com.arc.outland_horizon.utils.Utils.createModResourceLocation;
import static com.arc.outland_horizon.utils.Utils.itemName;
import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class ModRecipes extends RecipeProvider {
    static ArrayList<FinishedRecipe> finishedRecipes = new ArrayList<>();

    public ModRecipes(PackOutput pOutput) {
        super(pOutput);
    }

    public static void blockAndGemRecycle(Item blockItem, Item gemItem) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blockItem)
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', gemItem)
                .unlockedBy("has_item", hasItems(gemItem))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe), itemName(gemItem) + "_block_dismantle_to_gem_" + itemName(blockItem));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, gemItem, 9)
                .requires(blockItem)
                .unlockedBy("has_item", hasItems(blockItem))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe), itemName(blockItem) + "_gem_compose_to_block_" + itemName(gemItem));
    }

    public static void swordRecipe(Item item, Item sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, sword)
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void pickaxeRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("aaa")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void axeRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("aa ")
                .pattern("al ")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void shovelRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern(" a ")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void hoeRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("aa ")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void paxelRecipe(Item axe, Item pickaxe, Item shovel, Item paxel) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, paxel)
                .pattern("abc")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', axe)
                .define('b', pickaxe)
                .define('c', shovel)
                .define('l', Items.STICK)
                .unlockedBy("has_axe", hasItems(axe))
                .unlockedBy("has_pickaxe", hasItems(pickaxe))
                .unlockedBy("has_shovel", hasItems(shovel))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void hammerRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("aaa")
                .pattern("aaa")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void spadeRecipe(Item item, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("aaa")
                .pattern("ala")
                .pattern(" l ")
                .define('a', item).define('l', Items.STICK)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void destroyerRecipe(Item spade, Item hammer, Item tool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool)
                .pattern("alb")
                .pattern(" l ")
                .pattern(" l ")
                .define('a', spade)
                .define('b', hammer)
                .define('l', Items.STICK)
                .unlockedBy("has_spade", hasItems(spade))
                .unlockedBy("has_hammer", hasItems(hammer))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    public static void armorRecipe(Item item, Item helmet, Item chestplate, Item leggings, Item boots) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helmet)
                .pattern("aaa")
                .pattern("a a")
                .define('a', item)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, chestplate)
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', item)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, leggings)
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a', item)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, boots)
                .pattern("a a")
                .pattern("a a")
                .define('a', item)
                .unlockedBy("has_item", hasItems(item))
                .save(finishedRecipe -> finishedRecipes.add(finishedRecipe));
    }

    private static void toolPackRecipe(MutablePair<String,Boolean> pair) {
        String nameWithPrefix = pair.left;
        ModRecipes.swordRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_sword")));
        ModRecipes.pickaxeRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_pickaxe")));
        ModRecipes.axeRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_axe")));
        ModRecipes.shovelRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_shovel")));
        ModRecipes.hoeRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_hoe")));
        ModRecipes.paxelRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix + "_axe")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_pickaxe")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_shovel")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_paxel")));
        ModRecipes.hammerRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_hammer")));
        ModRecipes.spadeRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_spade")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix + "_shovel")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_hammer")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_destroyer")));
        ModRecipes.blockAndGemRecycle(getItemRegistered(createModResourceLocation(nameWithPrefix + "_block")), getItemRegistered(createModResourceLocation(nameWithPrefix)));
        ModRecipes.armorRecipe(getItemRegistered(createModResourceLocation(nameWithPrefix)), getItemRegistered(createModResourceLocation(nameWithPrefix + "_helmet")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_chestplate")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_leggings")), getItemRegistered(createModResourceLocation(nameWithPrefix + "_boots")));
    }

    public static void addRecipe(FinishedRecipe recipe) {
        finishedRecipes.add(recipe);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
    }
    public static int recipes(MinecraftServer server) throws IOException {
        int generated = 0;
        Set<ResourceLocation> recipes = server.getRecipeManager().getRecipeIds().collect(Collectors.toSet());
        vanillaExtendsRecipe();
        ToolPack.nameWithPrefixList.forEach(ModRecipes::toolPackRecipe);
        for (FinishedRecipe finishedRecipe : finishedRecipes) {
            if (recipes.contains(finishedRecipe.getId())) {
                continue;
            }
            File recipeFile = createFile(WorldUtils.getWorldFolderPath(server) + "\\todo\\recipe\\" + finishedRecipe.getId().getPath() + ".json");
            File advancementFile = createFile(WorldUtils.getWorldFolderPath(server) + "\\todo\\advancement\\" + finishedRecipe.getId().getPath() + ".json");

            try (BufferedWriter recipeWriter = new BufferedWriter(new FileWriter(recipeFile));
                 BufferedWriter advancementWriter = new BufferedWriter(new FileWriter(advancementFile))) {
                recipeWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(finishedRecipe.serializeRecipe()));
                advancementWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(finishedRecipe.serializeAdvancement()));
                generated++;
            } catch (IOException e) {
                Utils.LOGGER.error("Error writing to files", e);
            }
        }
        return generated;
    }

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            Utils.LOGGER.error("Failed to create directory: " + dir.getAbsolutePath());
            throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
        }
        if (!file.exists() && !file.createNewFile()) {
            Utils.LOGGER.error("Failed to create file: " + file.getAbsolutePath());
            throw new IOException("Failed to create file: " + file.getAbsolutePath());
        }
        return file;
    }

    private static void vanillaExtendsRecipe(){
        ModRecipes.paxelRecipe(Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, getItemRegistered(createModResourceLocation("wooden_paxel")));
        ModRecipes.paxelRecipe(Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, getItemRegistered(createModResourceLocation("stone_paxel")));
        ModRecipes.paxelRecipe(Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, getItemRegistered(createModResourceLocation("iron_paxel")));
        ModRecipes.paxelRecipe(Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, getItemRegistered(createModResourceLocation("golden_paxel")));
        ModRecipes.paxelRecipe(Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, getItemRegistered(createModResourceLocation("diamond_paxel")));
        ModRecipes.paxelRecipe(Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SHOVEL, getItemRegistered(createModResourceLocation("netherite_paxel")));
        ModRecipes.spadeRecipe(Items.COBBLESTONE, getItemRegistered(createModResourceLocation("stone_spade")));
        ModRecipes.spadeRecipe(Items.IRON_INGOT, getItemRegistered(createModResourceLocation("iron_spade")));
        ModRecipes.spadeRecipe(Items.GOLD_INGOT, getItemRegistered(createModResourceLocation("golden_spade")));
        ModRecipes.spadeRecipe(Items.DIAMOND, getItemRegistered(createModResourceLocation("diamond_spade")));
        ModRecipes.spadeRecipe(Items.NETHERITE_INGOT, getItemRegistered(createModResourceLocation("netherite_hammer")));
        ModRecipes.hammerRecipe(Items.COBBLESTONE, getItemRegistered(createModResourceLocation("stone_hammer")));
        ModRecipes.hammerRecipe(Items.IRON_INGOT, getItemRegistered(createModResourceLocation("iron_hammer")));
        ModRecipes.hammerRecipe(Items.GOLD_INGOT, getItemRegistered(createModResourceLocation("golden_hammer")));
        ModRecipes.hammerRecipe(Items.DIAMOND, getItemRegistered(createModResourceLocation("diamond_hammer")));
        ModRecipes.hammerRecipe(Items.NETHERITE_INGOT, getItemRegistered(createModResourceLocation("netherite_hammer")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation("stone_spade")), getItemRegistered(createModResourceLocation("stone_hammer")), getItemRegistered(createModResourceLocation("stone_destroyer")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation("iron_spade")), getItemRegistered(createModResourceLocation("iron_hammer")), getItemRegistered(createModResourceLocation("iron_destroyer")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation("golden_spade")), getItemRegistered(createModResourceLocation("golden_hammer")), getItemRegistered(createModResourceLocation("golden_destroyer")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation("diamond_spade")), getItemRegistered(createModResourceLocation("diamond_hammer")), getItemRegistered(createModResourceLocation("diamond_destroyer")));
        ModRecipes.destroyerRecipe(getItemRegistered(createModResourceLocation("netherite_spade")), getItemRegistered(createModResourceLocation("netherite_hammer")), getItemRegistered(createModResourceLocation("netherite_destroyer")));
    }
}
