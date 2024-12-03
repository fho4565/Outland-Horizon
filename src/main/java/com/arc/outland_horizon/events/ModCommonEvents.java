package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.client.gui.overlay.PlayerOverlay;
import com.arc.outland_horizon.develop.LangEN_US;
import com.arc.outland_horizon.develop.LangZH_CN;
import com.arc.outland_horizon.develop.ModLootTable;
import com.arc.outland_horizon.develop.ModRecipe;
import com.arc.outland_horizon.network.NetworkHandler;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true,
                new LangZH_CN(event.getGenerator().getPackOutput(), "zh_cn")
        );
        event.getGenerator().addProvider(
                true,
                new LangEN_US(event.getGenerator().getPackOutput(), "en_us")
        );
        event.getGenerator().addProvider(
                true,
                new ModRecipe(event.getGenerator().getPackOutput())
        );
        event.getGenerator().addProvider(
                true, new ModLootTable(event.getGenerator().getPackOutput())

        );
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(OutlandHorizon.MOD_ID, new PlayerOverlay());
    }
}
