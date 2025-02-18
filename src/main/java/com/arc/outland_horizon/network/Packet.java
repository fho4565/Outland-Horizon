package com.arc.outland_horizon.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface Packet {
    void encode(FriendlyByteBuf buf);

    void handle(Supplier<NetworkEvent.Context> ctx);
}
