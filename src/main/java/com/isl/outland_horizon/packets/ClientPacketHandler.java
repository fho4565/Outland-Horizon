package com.isl.outland_horizon.packets;

import com.isl.outland_horizon.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ClientPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Utils.MOD_ID, "channel_client"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID, "packet_client"),
                () -> PROTOCOL_VERSION,
                (version) -> version.equals(PROTOCOL_VERSION),
                (version) -> version.equals(PROTOCOL_VERSION)
        );
        INSTANCE.messageBuilder(ClientMessagePacket.class, id++)
                .encoder(ClientMessagePacket::encode)
                .decoder(ClientMessagePacket::decode)
                .consumerNetworkThread((packet, ctx)->{
                    packet.handler(packet,ctx);
                })
                .add();
    }

    public static void sendToServer(ClientMessagePacket packet) {
        INSTANCE.sendToServer(packet);
    }
}
