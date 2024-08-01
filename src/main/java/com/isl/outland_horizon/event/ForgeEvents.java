package com.isl.outland_horizon.event;

import com.isl.outland_horizon.Utils;
import com.isl.outland_horizon.level.capa.OhCapaHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if ( event.getEntity() instanceof Player player && event.getEntity().level().isClientSide) {
            //需要注意客户端的能力值关闭游戏就会消失需要你传到服务端
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(
                    Attributes -> {
                        Attributes.get("mana").setValue((Double)Attributes.get("mana").getValue()+100.0);
                        Utils.sendSimpleMessageToPlayer(player,"你当前的魔力值:"+Attributes.get("mana").getValue());




                    }
            );
        }
    }
}
