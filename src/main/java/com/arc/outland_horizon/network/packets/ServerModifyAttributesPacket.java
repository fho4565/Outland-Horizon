package com.arc.outland_horizon.network.packets;

import com.arc.outland_horizon.network.Packet;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerModifyAttributesPacket implements Packet {
    private final int operation;
    private final int target;

    public ServerModifyAttributesPacket(int operation, int target) {
        this.operation = operation;
        this.target = target;
    }

    public ServerModifyAttributesPacket(FriendlyByteBuf buf) {
        this.operation = buf.readByte();
        this.target = buf.readByte();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.operation);
        buf.writeByte(this.target);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer p = ctx.get().getSender();
            OhAttribute cap = null;
            if (p != null) {
                cap = ModCapabilities.getOhAttribute(p);
            }
            switch (this.target) {
                case 0 -> cap.setMana(this.operation);
                case 1 -> cap.setManaRecover(this.operation);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
