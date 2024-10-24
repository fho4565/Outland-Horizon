package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.registry.block.BlockRegistry;
import com.arc.outland_horizon.registry.item.Tools;
import com.arc.outland_horizon.registry.item.weapons.Melee;
import com.arc.outland_horizon.world.ModTiers;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;

public class ToolPack {
    public static ArrayList<MutablePair<String,Boolean>> nameWithPrefixList = new ArrayList<>();
    private Tier tier;
    private ToolPack() {
    }
    private ToolPack(Tier tier) {
        this.tier = tier;
    }

    public static void create(MaterialPack.MaterialType type, String name, float level,boolean dropBlockWhenOreBroken) {
        ToolPack materialPack = new ToolPack();
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> nameWithPrefix = name + "_gem";
            case DUST -> nameWithPrefix = name + "_dust";
            case INGOT -> nameWithPrefix = name + "_ingot";
            case CUSTOM -> nameWithPrefix = name;
        }
        materialPack.tier = ModTiers.createTier(level, nameWithPrefix);
        create(type, name, materialPack.tier, level,dropBlockWhenOreBroken);
    }

    public static void create(MaterialPack.MaterialType type, String name, Tier tier, float level,boolean dropBlockWhenOreBroken) {
        ToolPack materialPack = new ToolPack(tier);
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> nameWithPrefix = name + "_gem";
            case DUST -> nameWithPrefix = name + "_dust";
            case INGOT -> nameWithPrefix = name + "_ingot";
            case CUSTOM -> nameWithPrefix = name;
        }
        BlockRegistry.register("deep_"+nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.DEEPSLATE).strength(2.25f * level, 1.5f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(2.5f * level, 3.0f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(1.5f * level, 1.5f * level).requiresCorrectToolForDrops()));
        Melee.registerWeaponMelee(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_spade", () -> new Spade(materialPack.tier, new Item.Properties()));
        Tools.registerTool(nameWithPrefix + "_destroyer", () -> new Destroyer(materialPack.tier, new Item.Properties()));
        ToolPack.nameWithPrefixList.add(new MutablePair<>(nameWithPrefix,dropBlockWhenOreBroken));
    }
}
