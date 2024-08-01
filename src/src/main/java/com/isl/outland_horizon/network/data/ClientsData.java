package com.isl.outland_horizon.network.data;

import com.isl.outland_horizon.Utils;
import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.level.capa.data.OhAttribute;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ClientsData {
    public static void updateClientPlayerAttributes(int entityID, List<OhAttribute.ScapeApi> attributeList) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            Entity entity = clientLevel.getEntity(entityID);
            if (entity instanceof Player player) {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent((capability -> {
                    for (OhAttribute.ScapeApi attribute : attributeList) {
                        OhAttribute.ScapeApi target = capability.getProfession().get(attribute.getId());
                        if (target != null) {
                            target.copyFrom(attribute);
                            Utils.Info("游戏端拷贝服务端数据成功.");
                            target.setSync(false);
                        }
                    }
                }));
            }
        }
    }
}
