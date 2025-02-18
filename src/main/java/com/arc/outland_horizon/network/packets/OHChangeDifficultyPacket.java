package com.arc.outland_horizon.network.packets;

import com.arc.outland_horizon.core.ModDataManager;
import com.arc.outland_horizon.core.ModDifficulties;
import com.arc.outland_horizon.network.Packet;
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
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.difficulty.getId());
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer player = ctx.get().getSender();
        if (player != null) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                if (player.hasPermissions(2) || server.isSingleplayerOwner(player.getGameProfile())) {
                    ModDataManager.modDifficulties = this.difficulty;
                    ModDataManager.save();
                }
            }
        }
    }
}
