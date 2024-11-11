package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.client.gui.overlay.PlayerOverlay;
import com.arc.outland_horizon.develop.LangZH_CN;
import com.arc.outland_horizon.develop.ModRecipe;
import com.arc.outland_horizon.network.NetworkHandler;
import com.arc.outland_horizon.utils.Utils;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    /*@SubscribeEvent
    public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
        event.register(OhAttribute.class);
    }*/
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true,
                new LangZH_CN(event.getGenerator().getPackOutput(), "zh_cn")
        );
        event.getGenerator().addProvider(
                true,
                new ModRecipe(event.getGenerator().getPackOutput())
        );
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(Utils.MOD_ID, new PlayerOverlay());
    }
}
