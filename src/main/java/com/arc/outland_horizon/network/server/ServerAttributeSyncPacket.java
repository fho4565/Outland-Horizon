package com.arc.outland_horizon.network.server;

import com.arc.outland_horizon.network.Packet;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
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
        OhAttributeProvider.mana = nbt.getDouble("mana");
        OhAttributeProvider.manaRecover = nbt.getDouble("manaRecover");
        OhAttributeProvider.maxMana = nbt.getDouble("maxMana");
        OhAttributeProvider.rage = nbt.getDouble("rage");
        OhAttributeProvider.rageRecover = nbt.getDouble("rageRecover");
        OhAttributeProvider.maxRage = nbt.getDouble("maxRage");
        OhAttributeProvider.shieldValue = nbt.getDouble("shieldValue");
        ctx.get().enqueueWork(() ->
                ModCapabilities.getOhAttribute(Objects.requireNonNull(ctx.get().getSender())).deserializeNBT(nbt)
        );
        ctx.get().setPacketHandled(true);
    }
}
