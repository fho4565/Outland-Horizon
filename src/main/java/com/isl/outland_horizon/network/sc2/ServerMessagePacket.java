package com.isl.outland_horizon.network.sc2;

import com.isl.outland_horizon.network.IPacket;
import com.isl.outland_horizon.network.c2s.ClientMessagePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerMessagePacket  implements IPacket<ServerMessagePacket> {
    private String message;

    public ServerMessagePacket(String message) {
        this.message = message;
    }

    public ServerMessagePacket() {
    }


    @Override
    public void encode(ServerMessagePacket message, FriendlyByteBuf buffer) {
        buffer.writeUtf(message.message);
    }

    @Override
    public ServerMessagePacket decode(FriendlyByteBuf buffer) {
        return new ServerMessagePacket(buffer.readUtf(32767));
    }

    @Override
    public void handle(ServerMessagePacket message, Supplier<NetworkEvent.Context> supplier) {

    }
}
