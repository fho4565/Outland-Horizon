package com.isl.outland_horizon.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface Packet {
    void toBytes(FriendlyByteBuf buf);

    void handle(Supplier<NetworkEvent.Context> ctx);
}
