package com.isl.outland_horizon.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessagePacket {

    private final String message;

    public ClientMessagePacket(String message) {
        this.message = message;
    }

    public static void encode(ClientMessagePacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.message);
    }

    public static ClientMessagePacket decode(FriendlyByteBuf buffer) {
        return new ClientMessagePacket(buffer.readUtf(32767));
    }

    public void handler(ClientMessagePacket packet,Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientMessagePacket.handlePacket(packet, ctx));
        });
        ctx.get().setPacketHandled(true);
    }
    public static void handlePacket(ClientMessagePacket packet, Supplier<NetworkEvent.Context> ctx) {

    }
}
