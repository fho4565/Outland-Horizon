package com.isl.outland_horizon.network.isRemoveCode;

import com.isl.outland_horizon.Utils;
import com.isl.outland_horizon.network.isRemoveCode.packets.ServerMessagePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ServerPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Utils.MOD_ID, "channel_server"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID, "packet_server"),
                () -> PROTOCOL_VERSION,
                (version) -> version.equals(PROTOCOL_VERSION),
                (version) -> version.equals(PROTOCOL_VERSION)
        );
        INSTANCE.messageBuilder(ServerMessagePacket.class, id++)
                .encoder(ServerMessagePacket::encode)
                .decoder(ServerMessagePacket::decode)
                .consumerNetworkThread((packet, ctx)->{
                    packet.handler(packet,ctx);
                })
                .add();
    }

    public static void sendToServer(ServerMessagePacket packet) {
        INSTANCE.sendToServer(packet);
    }
    public static void sendToClient(ServerMessagePacket packet, Player player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayer) player), packet);
    }
    public static void sendToClientChunk(ServerMessagePacket packet, LevelChunk levelChunk){
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->levelChunk), packet);
    }
    public static void sendToClientAll(ServerMessagePacket packet){
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}
