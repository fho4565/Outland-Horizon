package com.arc.outland_horizon.network;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.network.server.C2SPacket;
import com.arc.outland_horizon.network.server.ServerAttributeSyncPacket;
import com.arc.outland_horizon.network.server.ServerModifyAttributesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1.0";
    public static SimpleChannel INSTANCE;
    private static int id;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(OutlandHorizon.MOD_ID, "common"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.messageBuilder(ServerModifyAttributesPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ServerModifyAttributesPacket::new)
                .encoder(ServerModifyAttributesPacket::toBytes)
                .consumerMainThread(ServerModifyAttributesPacket::handle)
                .add();

        INSTANCE.messageBuilder(ServerAttributeSyncPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ServerAttributeSyncPacket::new)
                .encoder(ServerAttributeSyncPacket::toBytes)
                .consumerMainThread(ServerAttributeSyncPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SPacket.class, id++)
                .decoder(C2SPacket::new)
                .encoder(C2SPacket::toBytes)
                .consumerMainThread(C2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, Player player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }

}
