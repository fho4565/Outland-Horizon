package com.arc.outland_horizon.network;

import com.arc.outland_horizon.ModDifficulties;
import com.arc.outland_horizon.OHDataManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OHChangeDifficultyPacket implements Packet {
    private final ModDifficulties difficulty;

    public OHChangeDifficultyPacket(ModDifficulties pDifficulty) {
        this.difficulty = pDifficulty;
    }

    public OHChangeDifficultyPacket(FriendlyByteBuf pBuffer) {
        this.difficulty = ModDifficulties.byId(pBuffer.readUnsignedByte());
    }

    public ModDifficulties getDifficulty() {
        return this.difficulty;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(this.difficulty.getId());
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();
        if (player != null) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                if (player.hasPermissions(2) || server.isSingleplayerOwner(player.getGameProfile())) {
                    OHDataManager.modDifficulties = this.difficulty;
                    OHDataManager.save();
                }
            }
        }
    }
}
