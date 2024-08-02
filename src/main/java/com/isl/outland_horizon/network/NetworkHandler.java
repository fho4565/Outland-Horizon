package com.isl.outland_horizon.network;

import com.isl.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID, "network"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
    }

    public static <P> void sendToClientPlayer(Player player, P packet) {
        if (player == null) {
            sendToAllClients(packet);
        } else {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
        }
    }

    public static <P> void sendToClientChunk(LevelChunk levelChunk, P packet) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> levelChunk), packet);
    }

    public static <P> void sendToAllClients(P packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static <P> void sendToServer(P packet) {
        INSTANCE.sendToServer(packet);
    }

}
