package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangEN_US extends LanguageProvider {
    public LangEN_US(PackOutput output, String locale) {
        super(output, OutlandHorizon.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(OHItems.Tool.BLOOD_BUCKET, "Blood bucket");
        addItem(OHItems.Tool.BLOOD_STONE_AXE, "Blood stone axe");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK, "Dungeon brick");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_TILE, "Dungeon brick tile");
        addBlock(OHBlocks.Building.DUNGEON.DUNGEON_BRICK_PILLAR, "Dungeon brick pillar");
        addBlock(OHBlocks.Building.DUNGEON.ZOMBIE_DUNGEON_BRICK, "Zombie dungeon brick");
        addBlock(OHBlocks.Building.DUNGEON.SKELETON_DUNGEON_BRICK, "Skeleton dungeon brick");
        addBlock(OHBlocks.Building.DUNGEON.DAMAGED_DUNGEON_BRICK, "Damaged dungeon brick");
        addBlock(OHBlocks.Building.DUNGEON.WORN_DUNGEON_BRICK, "Worn dungeon brick");
        addBlock(OHBlocks.Functional.DUNGEON_TORCH, "Dungeon torch");
    }
}
