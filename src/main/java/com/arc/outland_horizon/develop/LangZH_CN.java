package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangZH_CN extends LanguageProvider {
    public LangZH_CN(PackOutput output, String locale) {
        super(output, Utils.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(OHItems.Tool.BLOOD_BUCKET, "血桶");
        addItem(OHItems.Tool.BLOOD_STONE_AXE, "血石斧");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK, "地牢砖");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_TILE, "地牢砖瓦");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_PILLAR, "地牢砖柱");
        addBlock(OHBlocks.Building.DUNGEON.ZOMBIE_DUNGEON_BRICK, "僵尸地牢砖");
        addBlock(OHBlocks.Building.DUNGEON.SKELETON_DUNGEON_BRICK, "骷髅地牢砖");
        addBlock(OHBlocks.Building.DUNGEON.DAMAGED_DUNGEON_BRICK, "损坏地牢砖");
        addBlock(OHBlocks.Building.DUNGEON.WORN_DUNGEON_BRICK, "磨损地牢砖");
        addBlock(OHBlocks.Functional.DUNGEON_TORCH, "地牢火把");
    }
}
