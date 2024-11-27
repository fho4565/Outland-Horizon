package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.client.key.KeyRegistry;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        KeyRegistry.register(event);
    }
    @SubscribeEvent
    public static void addItemToVanillaTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS)) {
            event.accept(OHBlocks.Functional.TEXTURES_TEST_BLOCK);
            event.accept(OHItems.Tool.DEBUGGER);
        }
    }
}
