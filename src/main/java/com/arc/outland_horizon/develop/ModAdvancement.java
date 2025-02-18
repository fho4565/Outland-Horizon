package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHDimensions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancement extends ForgeAdvancementProvider {
    final static ArrayList<Advancement> list = new ArrayList<>();

    public ModAdvancement(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementSubProvider()));
    }

    public static class ModAdvancementSubProvider implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            init();
            list.forEach(saver);
        }
    }

    private static void init() {
        list.add(Advancement.Builder.advancement()
                .display(OHBlocks.Natural.BLOOD_STONE_BLOCK.get(), Component.translatable("advancements.end.root.title"), Component.translatable("advancements.end.root.description"), OutlandHorizon.createModResourceLocation("textures/block/natural/nightmare_dirt.png"), FrameType.TASK, true, true, false)
                .addCriterion("entered_nightmare", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(OHDimensions.NIGHTMARE))
                .build(OutlandHorizon.createModResourceLocation("nightmare/root"))
        );
    }
}
