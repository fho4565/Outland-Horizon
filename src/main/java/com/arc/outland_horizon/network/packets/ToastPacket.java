package com.arc.outland_horizon.network.packets;

import com.arc.outland_horizon.client.CustomToast;
import com.arc.outland_horizon.network.Packet;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToastPacket implements Packet {
    private final String message;

    public ToastPacket(String message) {
        this.message = message;
    }


    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(message);
    }

    public static ToastPacket decode(FriendlyByteBuf buffer) {
        return new ToastPacket(buffer.readUtf(32767));
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            try {
                Minecraft.getInstance().getToasts().addToast(CustomToast.load(message));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }));
        ctx.get().setPacketHandled(true);
    }
}
