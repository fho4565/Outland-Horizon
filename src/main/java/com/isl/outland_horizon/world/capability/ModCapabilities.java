package com.isl.outland_horizon.world.capability;

import com.isl.outland_horizon.network.NetworkHandler;
import com.isl.outland_horizon.network.server.ServerAttributeSyncPacket;
import com.isl.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<OhAttribute> OH_ATTRIBUTE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static OhAttribute getOhAttribute(Player player) {
        return player.getCapability(OH_ATTRIBUTE).orElse(new OhAttribute(player));
    }

    public static void serverSyncAttribute(Player player) {
        NetworkHandler.sendToPlayer(new ServerAttributeSyncPacket(getOhAttribute(player)), player);
    }

}
