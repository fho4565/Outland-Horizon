package com.isl.oh.register;

import com.isl.oh.packets.ClientPacketHandler;
import com.isl.oh.packets.ServerPacketHandler;
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
