package com.isl.outland_horizon.utils;

import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import com.isl.outland_horizon.world.item.tools.multi.Destroyer;
import com.isl.outland_horizon.world.item.tools.multi.Hammer;
import com.isl.outland_horizon.world.item.tools.multi.Paxel;
import com.isl.outland_horizon.world.item.tools.multi.Spade;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class ToolPack {
    private Tier tier;
    private ToolPack() {
    }
    private ToolPack(Tier tier) {
        this.tier = tier;
    }

    public static void create(MaterialType type, String name, float level) {
        ToolPack materialPack = new ToolPack();
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                ItemRegistry.register(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                ItemRegistry.register(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                ItemRegistry.register(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                ItemRegistry.register(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        String finalNameWithPrefix = nameWithPrefix;
        materialPack.tier = new Tier() {
            @Override
            public int getUses() {
                return (int) (250 * level);
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
                return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation(finalNameWithPrefix)).get());
            }
        };
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(0.15f * level, 0.3f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(0.2f * level, 0.2f * level).requiresCorrectToolForDrops()));
        ItemRegistry.register(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_spade", () -> new Spade(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_destroyer", () -> new Destroyer(materialPack.tier, new Item.Properties()));
    }

    public static void create(MaterialType type, String name, Tier tier,float level) {
        ToolPack materialPack = new ToolPack(tier);
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                ItemRegistry.register(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                ItemRegistry.register(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                ItemRegistry.register(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                ItemRegistry.register(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(0.15f * level, 0.3f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(0.2f * level, 0.2f * level).requiresCorrectToolForDrops()));
        ItemRegistry.register(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_spade", () -> new Spade(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_destroyer", () -> new Destroyer(materialPack.tier, new Item.Properties()));
    }

    public enum MaterialType {
        GEM, DUST, INGOT, CUSTOM
    }
}
