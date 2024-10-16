package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.world.ModTiers;
import com.arc.outland_horizon.world.block.BlockRegistry;
import com.arc.outland_horizon.registry.item.Materials;
import com.arc.outland_horizon.registry.item.Tools;
import com.arc.outland_horizon.registry.item.weapons.Melee;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

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
                Materials.registerMaterial(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                Materials.registerMaterial(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                Materials.registerMaterial(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                Materials.registerMaterial(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        materialPack.tier = ModTiers.createTier(level, nameWithPrefix);
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(0.15f * level, 0.3f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(0.2f * level, 0.2f * level).requiresCorrectToolForDrops()));
        Melee.registerWeaponMelee(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_spade", () -> new Spade(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_destroyer", () -> new Destroyer(materialPack.tier, new Item.Properties()));
    }

    public static void create(MaterialType type, String name, Tier tier,float level) {
        ToolPack materialPack = new ToolPack(tier);
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                Materials.registerMaterial(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                Materials.registerMaterial(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                Materials.registerMaterial(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                Materials.registerMaterial(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        BlockRegistry.register("deep_"+nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.DEEPSLATE).strength(0.16f * level, 0.35f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(0.15f * level, 0.3f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(0.2f * level, 0.2f * level).requiresCorrectToolForDrops()));
        Melee.registerWeaponMelee(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_spade", () -> new Spade(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_destroyer", () -> new Destroyer(materialPack.tier, new Item.Properties()));
    }

    public enum MaterialType {
        GEM, DUST, INGOT, CUSTOM
    }
}
