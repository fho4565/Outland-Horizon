package com.isl.outland_horizon.network.c2s;

import com.isl.outland_horizon.network.IPacket;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessagePacket implements IPacket<ClientMessagePacket>{

      private String message;

      public ClientMessagePacket(String message) {
        this.message = message;
    }

      public ClientMessagePacket() {
      }


    @Override
        public void encode(ClientMessagePacket message, FriendlyByteBuf buffer) {

          buffer.writeUtf(message.message);
        }

        @Override
        public ClientMessagePacket decode(FriendlyByteBuf buffer) {

            return new ClientMessagePacket(buffer.readUtf(32767));
        }

        @Override
        public void handle(ClientMessagePacket message, Supplier<NetworkEvent.Context> supplier) {

        }



}