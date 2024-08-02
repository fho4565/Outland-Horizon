package com.isl.outland_horizon.network;

import com.isl.outland_horizon.network.c2s.CapaToServerPacker;
import com.isl.outland_horizon.network.c2s.ClientMessagePacket;
import com.isl.outland_horizon.network.s2c.AttributesToClientPacket;
import com.isl.outland_horizon.network.s2c.ServerMessagePacket;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class PacketHandler {
    public static final String PROTOCOL_VERSION = Utils.MOD_ID + "1.0";
    public static SimpleChannel simpleChannel;
    private static int tempId;

    public static void register()
    {
        simpleChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Utils.MOD_ID, "common"), ()-> PROTOCOL_VERSION,
                (s) ->true, (s) ->true);
        registerPacket(CapaToServerPacker.class, new CapaToServerPacker());
        registerPacket(AttributesToClientPacket.class, new AttributesToClientPacket());
        registerPacket(ClientMessagePacket.class, new ClientMessagePacket());
        registerPacket(ServerMessagePacket.class, new ServerMessagePacket());
    }

    private static <T> void registerPacket(Class<T> clazz, IPacket<T> message) {
        simpleChannel.registerMessage(tempId++, clazz, message::encode, message::decode, message::handle);
    }

    public static <MSG> void sendToServer(MSG message){
        simpleChannel.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        simpleChannel.send(PacketDistributor.PLAYER.with(()->player),message);
    }

}
