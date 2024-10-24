package com.arc.outland_horizon.network.server;

import com.arc.outland_horizon.network.Packet;
import com.arc.outland_horizon.registry.mod_effect.MobEffectRegistry;
import com.arc.outland_horizon.utils.RageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class C2SPacket implements Packet {
    public static class Operation {
        public static final int TRIGGER_RAGE = 0;
    }
    private final int operation;

    public C2SPacket(int operation) {
        this.operation = operation;
    }

    public C2SPacket(FriendlyByteBuf buf) {
        this.operation = buf.readByte();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(this.operation);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer p = Objects.requireNonNull(ctx.get().getSender());
        switch (this.operation){
            case Operation.TRIGGER_RAGE -> {
                if(RageUtils.isRageFull(p)){
                    p.addEffect(new MobEffectInstance(MobEffectRegistry.RAGE.get(), 600));
                    RageUtils.setRage(p, 0);
                }
            }
        }
        ctx.get().setPacketHandled(true);
    }
}
