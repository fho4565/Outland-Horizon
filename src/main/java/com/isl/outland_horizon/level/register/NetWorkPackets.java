package com.isl.outland_horizon.level.register;

import com.isl.outland_horizon.network.ClientPacketHandler;
import com.isl.outland_horizon.network.ServerPacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetWorkPackets {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ServerPacketHandler::registerPackets);
        event.enqueueWork(ClientPacketHandler::registerPackets);
    }
}
