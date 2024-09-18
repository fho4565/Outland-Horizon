package com.isl.outland_horizon.network.server;

import com.isl.outland_horizon.network.Packet;
import com.isl.outland_horizon.world.capability.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerAttributeSyncPacket implements Packet {
    private final CompoundTag nbt;

    public ServerAttributeSyncPacket(INBTSerializable<CompoundTag> cap) {
        this.nbt = cap.serializeNBT();
    }

    public ServerAttributeSyncPacket(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Client
            ModCapabilities.getOhAttribute(ctx.get().getSender()).deserializeNBT(nbt);
        });
        ctx.get().setPacketHandled(true);
    }
}
