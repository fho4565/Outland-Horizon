package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.core.LogPack;
import com.arc.outland_horizon.core.MaterialPack;
import com.arc.outland_horizon.core.ModPacks;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.ItemUtils;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class ModRecipe extends RecipeProvider {
    public ModRecipe(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        Init.Consumable.initPotions(writer);
        materialPack(writer, ModPacks.BLOOD_STONE);
        materialPack(writer, ModPacks.BLUE_GEM);
        woodThings(writer, ModPacks.NIGHTMARE_LOG);
        woodThings(writer, ModPacks.COAGULATED_NIGHTMARE_LOG);
        oreFamily(writer, ModPacks.BLUE_GEM);
        oreFamily(writer, ModPacks.BLOOD_STONE);
    }

    private void vanilla(Consumer<FinishedRecipe> writer) {
        paxel(writer, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, OHItems.Tool.WOODEN_PAXEL.get());

        paxel(writer, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, OHItems.Tool.STONE_PAXEL.get());
        spade(writer, Items.STONE, OHItems.Tool.STONE_SPADE.get());
        hammer(writer, Items.STONE, OHItems.Tool.STONE_HAMMER.get());
        destroyer(writer, OHItems.Tool.STONE_SPADE.get(), OHItems.Tool.STONE_HAMMER.get(), OHItems.Tool.STONE_DESTROYER.get());

        paxel(writer, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, OHItems.Tool.IRON_PAXEL.get());
        spade(writer, Items.IRON_INGOT, OHItems.Tool.IRON_SPADE.get());
        hammer(writer, Items.IRON_INGOT, OHItems.Tool.IRON_HAMMER.get());
        destroyer(writer, OHItems.Tool.IRON_SPADE.get(), OHItems.Tool.IRON_HAMMER.get(), OHItems.Tool.IRON_DESTROYER.get());

        paxel(writer, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, OHItems.Tool.GOLDEN_PAXEL.get());
        spade(writer, Items.GOLD_INGOT, OHItems.Tool.GOLDEN_SPADE.get());
        hammer(writer, Items.GOLD_INGOT, OHItems.Tool.GOLDEN_HAMMER.get());
        destroyer(writer, OHItems.Tool.GOLDEN_SPADE.get(), OHItems.Tool.GOLDEN_HAMMER.get(), OHItems.Tool.GOLDEN_DESTROYER.get());

        paxel(writer, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, OHItems.Tool.DIAMOND_PAXEL.get());
        spade(writer, Items.DIAMOND, OHItems.Tool.DIAMOND_SPADE.get());
        hammer(writer, Items.DIAMOND, OHItems.Tool.DIAMOND_HAMMER.get());
        destroyer(writer, OHItems.Tool.DIAMOND_SPADE.get(), OHItems.Tool.DIAMOND_HAMMER.get(), OHItems.Tool.DIAMOND_DESTROYER.get());

        netheriteSmithing(writer, OHItems.Tool.DIAMOND_PAXEL.get(), RecipeCategory.TOOLS, OHItems.Tool.NETHERITE_PAXEL.get());
        netheriteSmithing(writer, OHItems.Tool.DIAMOND_SPADE.get(), RecipeCategory.TOOLS, OHItems.Tool.NETHERITE_SPADE.get());
        netheriteSmithing(writer, OHItems.Tool.DIAMOND_HAMMER.get(), RecipeCategory.TOOLS, OHItems.Tool.NETHERITE_HAMMER.get());
        netheriteSmithing(writer, OHItems.Tool.DIAMOND_DESTROYER.get(), RecipeCategory.TOOLS, OHItems.Tool.NETHERITE_DESTROYER.get());
    }

    private void oreFamily(Consumer<FinishedRecipe> writer, MaterialPack family) {
        Item dust = family.get(MaterialPack.ItemVariant.DUST);
        Item nugget = family.get(MaterialPack.ItemVariant.NUGGET);
        Item gem = family.get(MaterialPack.ItemVariant.GEM);
        Item ingot = family.get(MaterialPack.ItemVariant.INGOT);
        Block ore = family.get(MaterialPack.BlockVariant.ORE);
        Block deepOre = family.get(MaterialPack.BlockVariant.DEEP_ORE);
        Block block = family.get(MaterialPack.BlockVariant.BLOCK);
        float exp = family.exp();
        int speed = family.time();
        Item material = nugget != null ? nugget : ingot != null ? ingot : dust != null ? dust : gem;
        toBlockCreate(writer, block, material);
        blockToCreate(writer, block, material);
        if (ore != null) {
            oreSmeltCreate(writer, ore, material, exp, speed);
        }
        if (deepOre != null) {
            oreSmeltCreate(writer, deepOre, material, exp, speed);
        }

    }

    private void toBlockCreate(Consumer<FinishedRecipe> writer, Block block, Item item) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, block)
                .requires(item, 9)
                .unlockedBy("criteria", hasItems(item))
                .save(writer);
    }

    private void blockToCreate(Consumer<FinishedRecipe> writer, Block block, Item item) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item, 9)
                .requires(block)
                .unlockedBy("criteria", hasItems(block))
                .save(writer);
    }

    private void oreSmeltCreate(Consumer<FinishedRecipe> writer, Block block, Item item, float exp, int speed) {
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(block),
                        RecipeCategory.MISC,
                        item,
                        exp,
                        speed
                )
                .unlockedBy("criteria", hasItems(block))
                .save(writer, OutlandHorizon.createModResourceLocation(getItemName(block) + "_from_smelting_" + getItemName(item)));
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(block),
                        RecipeCategory.MISC,
                        item,
                        exp, speed / 2
                ).unlockedBy("criteria", hasItems(block))
                .save(writer, OutlandHorizon.createModResourceLocation(getItemName(block) + "_from_blasting_" + getItemName(item)));
    }

    private void smeltCreate(Consumer<FinishedRecipe> writer, Item input, Item output, float exp, int speed) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, exp, speed)
                .unlockedBy("criteria", hasItems(input))
                .save(writer, OutlandHorizon.createModResourceLocation(getItemName(input) + "_smelting_to_" + getItemName(output)));
    }

    private void smeltCreate(Consumer<FinishedRecipe> writer, Item input, Item output) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, 0.1f, 200)
                .unlockedBy("criteria", hasItems(input))
                .save(writer, OutlandHorizon.createModResourceLocation(getItemName(input) + "_smelting_to_" + getItemName(output)));
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

    private void woodThings(Consumer<FinishedRecipe> writer, LogPack logPack) {
        Block log = logPack.get(LogPack.Variant.LOG);
        Block stripped_log = logPack.get(LogPack.Variant.STRIPPED_LOG);
        Block wood = logPack.get(LogPack.Variant.WOOD);
        Block stripped_wood = logPack.get(LogPack.Variant.STRIPPED_WOOD);
        Item planks = logPack.get(LogPack.Variant.PLANKS).asItem();
        Item pressure_plate = logPack.get(LogPack.Variant.PRESSURE_PLATE).asItem();
        Item trapdoor = logPack.get(LogPack.Variant.TRAPDOOR).asItem();
        Item button = logPack.get(LogPack.Variant.BUTTON).asItem();
        Item stairs = logPack.get(LogPack.Variant.STAIRS).asItem();
        Item slab = logPack.get(LogPack.Variant.SLAB).asItem();
        Item fence_gate = logPack.get(LogPack.Variant.FENCE_GATE).asItem();
        Item fence = logPack.get(LogPack.Variant.FENCE).asItem();
        Item door = logPack.get(LogPack.Variant.DOOR).asItem();
        if (log != null) {
            logSplit(writer, log.asItem(), wood.asItem(), planks);
            logSplit(writer, stripped_log.asItem(), stripped_wood.asItem(), planks);
        }

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

    private void materialPack(Consumer<FinishedRecipe> writer, MaterialPack materialPack) {
        Item nugget = materialPack.get(MaterialPack.ItemVariant.NUGGET);
        Item ingot = materialPack.get(MaterialPack.ItemVariant.INGOT);
        Item dust = materialPack.get(MaterialPack.ItemVariant.DUST);
        Item gem = materialPack.get(MaterialPack.ItemVariant.GEM);
        Item material = nugget != null ? nugget : ingot != null ? ingot : dust != null ? dust : gem;

        Item sword = materialPack.get(MaterialPack.ItemVariant.SWORD);
        Item pickaxe = materialPack.get(MaterialPack.ItemVariant.PICKAXE);
        Item axe = materialPack.get(MaterialPack.ItemVariant.AXE);
        Item shovel = materialPack.get(MaterialPack.ItemVariant.SHOVEL);
        Item hoe = materialPack.get(MaterialPack.ItemVariant.HOE);
        Item paxel = materialPack.get(MaterialPack.ItemVariant.PAXEL);
        Item spade = materialPack.get(MaterialPack.ItemVariant.SPADE);
        Item hammer = materialPack.get(MaterialPack.ItemVariant.HAMMER);
        Item destroyer = materialPack.get(MaterialPack.ItemVariant.DESTROYER);

        Item helmet = materialPack.get(MaterialPack.ItemVariant.HELMET);
        Item chestplate = materialPack.get(MaterialPack.ItemVariant.CHESTPLATE);
        Item leggings = materialPack.get(MaterialPack.ItemVariant.LEGGINGS);
        Item boots = materialPack.get(MaterialPack.ItemVariant.BOOTS);

        sword(writer, material, sword);
        pickaxe(writer, material, pickaxe);
        axe(writer, material, axe);
        shovel(writer, material, shovel);
        hoe(writer, material, hoe);
        paxel(writer, axe, pickaxe, shovel, paxel);
        spade(writer, material, spade);
        hammer(writer, material, hammer);
        destroyer(writer, spade, hammer, destroyer);
        helmet(writer, material, helmet);
        chestplate(writer, material, chestplate);
        leggings(writer, material, leggings);
        boots(writer, material, boots);
        if (nugget != null) {
            shapeless(writer, Ingredient.of(new ItemStack(nugget, 9)), new ItemStack(ingot));
            smeltCreate(writer, sword, nugget);
            smeltCreate(writer, pickaxe, nugget);
            smeltCreate(writer, axe, nugget);
            smeltCreate(writer, shovel, nugget);
            smeltCreate(writer, hoe, nugget);
            smeltCreate(writer, paxel, nugget);
            smeltCreate(writer, spade, nugget);
            smeltCreate(writer, hammer, nugget);
            smeltCreate(writer, destroyer, nugget);
            smeltCreate(writer, helmet, nugget);
            smeltCreate(writer, chestplate, nugget);
            smeltCreate(writer, leggings, nugget);
            smeltCreate(writer, boots, nugget);
        }
    }

    private static void shapeless(Consumer<FinishedRecipe> writer, Ingredient input, ItemStack output) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output.getItem(), output.getCount());
        for (ItemStack itemStack : input.getItems()) {
            builder.requires(itemStack.getItem(), itemStack.getCount());
        }
        builder.unlockedBy("has_item", hasItems(Arrays.stream(input.getItems())
                        .map(ItemStack::getItem).toArray(Item[]::new)))
                .save(writer, OutlandHorizon.createModResourceLocation(Utils.getDescriptionIdName(input.getItems()[0].getItem()) + "_to_" + Utils.getDescriptionIdName(output.getItem())));
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

    private void helmet(Consumer<FinishedRecipe> writer, Item material, Item helmet) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("aaa")
                .pattern("a a")
                .define('a', material)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void chestplate(Consumer<FinishedRecipe> writer, Item material, Item chestplate) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', material)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void leggings(Consumer<FinishedRecipe> writer, Item material, Item leggings) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a', material)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private void boots(Consumer<FinishedRecipe> writer, Item material, Item boots) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("a a")
                .pattern("a a")
                .define('a', material)
                .unlockedBy("criteria", hasItems(material))
                .save(writer);
    }

    private static class Init {
        public static class Material {

            public static void init(Consumer<FinishedRecipe> writer) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OHItems.Material.WATER_GEMSTONE.get())
                        .pattern("aba")
                        .pattern("bab")
                        .pattern("aba")
                        .define('a', OHItems.Material.WATER_GEMSTONE.get())
                        .define('b', Items.WATER_BUCKET)
                        .unlockedBy("has_item", hasItems(OHItems.Material.WATER_GEMSTONE.get()))
                        .save(writer);
            }
        }

        public static class Consumable {
            public static void initPotions(Consumer<FinishedRecipe> writer) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, OHItems.Consumable.RAGE_POTION.get())
                        .pattern("yhy")
                        .pattern("ldl")
                        .pattern(" s ")
                        .define('y', Items.GLOWSTONE_DUST)
                        .define('h', Items.GUNPOWDER)
                        .define('l', Items.BLAZE_ROD)
                        .define('d', Items.NETHER_WART)
                        .define('s', Ingredient.of(ItemUtils.getPotion(Potions.WATER)))
                        .unlockedBy("has_item", hasItems(Items.POTION, Items.GLOWSTONE_DUST, Items.GUNPOWDER, Items.BLAZE_ROD, Items.NETHER_WART))
                        .save(writer);
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, OHItems.Consumable.MANA_POTION_COMMON.get())
                        .requires(ItemTags.FLOWERS)
                        .requires(Ingredient.of(ItemUtils.getPotion(Potions.WATER)))
                        .unlockedBy("has_item_tag", has(ItemTags.FLOWERS))
                        .unlockedBy("has_item", has(Items.POTION))
                        .save(writer);
            }
        }

    }
}
