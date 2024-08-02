package com.isl.outland_horizon.network.s2c;

import com.isl.outland_horizon.level.capa.data.OhAttribute;
import com.isl.outland_horizon.network.IPacket;
import com.isl.outland_horizon.network.data.ClientsData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AttributesToClientPacket implements IPacket<AttributesToClientPacket> {
    List<OhAttribute.ScapeApi> attributeList;
    int entityID;

    public AttributesToClientPacket(List<OhAttribute.ScapeApi> attributeList, int entityID) {
        this.attributeList = attributeList;
        this.entityID = entityID;
    }

    public AttributesToClientPacket() {
    }

    @Override
    public void encode(AttributesToClientPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.attributeList.size());
        buffer.writeInt(message.entityID);
        for (OhAttribute.ScapeApi attribute : message.attributeList) {
            attribute.serialize(buffer);
        }
    }

    @Override
    public AttributesToClientPacket decode(FriendlyByteBuf buffer) {
        int size = buffer.readInt();
        int entityID = buffer.readInt();
        List<OhAttribute.ScapeApi> attributes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            attributes.add(OhAttribute.ScapeApi.deserialize(buffer));
        }
        return new AttributesToClientPacket(attributes, entityID);
    }

    @Override
    public void handle(AttributesToClientPacket message, Supplier<NetworkEvent.Context> supplier) {

        supplier.get().enqueueWork(() -> {

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientsData.updateClientPlayerAttributes(message.entityID, message.attributeList));



        });
        supplier.get().setPacketHandled(true);
    }
}
