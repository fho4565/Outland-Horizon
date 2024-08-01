package com.isl.outland_horizon.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerMessagePacket {
    private final String message;

    public ServerMessagePacket(String message) {
        this.message = message;
    }

    public static void encode(ServerMessagePacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.message);
    }

    public static ServerMessagePacket decode(FriendlyByteBuf buffer) {
        return new ServerMessagePacket(buffer.readUtf(32767));
    }

    public void handler(ServerMessagePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();

        });

        ctx.get().setPacketHandled(true);
    }
}
