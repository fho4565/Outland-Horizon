package com.isl.outland_horizon.network.s2c;

import com.isl.outland_horizon.network.IPacket;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
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
        Utils.Info("测试序列化");
        buffer.writeUtf(message.message);
    }

    @Override
    public ServerMessagePacket decode(FriendlyByteBuf buffer) {
        Utils.Info("测试反序列化");
        return new ServerMessagePacket(buffer.readUtf(32767));
    }

    @Override
    public void handle(ServerMessagePacket message, Supplier<NetworkEvent.Context> supplier) {

    }
}
