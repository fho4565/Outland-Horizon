package com.isl.outland_horizon.network.c2s;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.level.capa.data.OhAttribute;
import com.isl.outland_horizon.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CapaToServerPacker implements IPacket<CapaToServerPacker> {

    List<OhAttribute.ScapeApi> ListMap;

    public CapaToServerPacker() {
    }
    public CapaToServerPacker(List< OhAttribute.ScapeApi> MapData) {
    this.ListMap=MapData;
    }



    @Override
    public void encode(CapaToServerPacker message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.ListMap.size());
        for (OhAttribute.ScapeApi attribute : message.ListMap) {
            attribute.serialize(buffer);
        }
    }

    @Override
    public CapaToServerPacker decode(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        List<OhAttribute.ScapeApi> attributes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            attributes.add(OhAttribute.ScapeApi.deserialize(buffer));
        }
        return new CapaToServerPacker(attributes);
    }

    @Override
    public void handle(CapaToServerPacker message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            if (message.ListMap.isEmpty()) {
                return;
            }
            ServerPlayer player = supplier.get().getSender();
            if (player != null) {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent((capability -> {
                    boolean markNeedBroadCast = false;
                    for (OhAttribute.ScapeApi attribute : message.ListMap) {
                        OhAttribute.ScapeApi target = capability.getProfession().get(attribute.getId());
                        if (target != null) {
                            target.copyFrom(attribute);
                            if (!target.getC2S()) {
                                target.setSync(true);
                                markNeedBroadCast = true;
                            } else {
                                target.setSync(false);
                            }
                        }
                    }
                    capability.setNeedSync(markNeedBroadCast);
                }));
            }
        });
        supplier.get().setPacketHandled(true);
    }
}

