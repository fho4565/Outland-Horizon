package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.client.gui.overlay.PlayerOverlay;
import com.arc.outland_horizon.core.ArmorSuits;
import com.arc.outland_horizon.develop.*;
import com.arc.outland_horizon.network.NetworkHandler;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
        event.enqueueWork(() -> {
            ModBrewingRecipes.generate();
            ModBrewingRecipes.getAllBrewingRecipes().forEach(BrewingRecipeRegistry::addRecipe);
        });
        ArmorSuits.init();
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true,
                new ModRecipe(event.getGenerator().getPackOutput())
        );
        event.getGenerator().addProvider(true,
                new ModTag.ItemTag(event.getGenerator().getPackOutput(), Registries.ITEM, event.getLookupProvider(), event.getExistingFileHelper())
        );
        event.getGenerator().addProvider(true,
                new ModTag.BlockTag(event.getGenerator().getPackOutput(), Registries.BLOCK, event.getLookupProvider(), event.getExistingFileHelper())
        );
        event.getGenerator().addProvider(
                true, new ModLootTable(event.getGenerator().getPackOutput())
        );
        event.getGenerator().addProvider(
                true, new ModAdvancement(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper())
        );

    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll(OutlandHorizon.MOD_ID, PlayerOverlay.of());
    }
}
