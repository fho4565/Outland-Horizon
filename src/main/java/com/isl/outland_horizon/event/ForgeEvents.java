package com.isl.outland_horizon.event;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if ( event.getEntity() instanceof Player player ) {
            if (!player.level().isClientSide) {
                //需要注意客户端的能力值关闭游戏就会消失需要你传到服务端
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(
                        Attributes -> {
                            Utils.sendSimpleMessageToPlayer(player, "[测试]你当前的魔力值:" + Attributes.get("mana").getValue());
                        }
                );
            }
        }
    }


    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity EntitySource = event.getSource().getEntity();
        if (EntitySource instanceof Player player) {
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(ohAttributes -> {
                ohAttributes.setAttrValue("mana", (Double)ohAttributes.getAttrValue("mana") + 0.5);
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(
                            ohAttributes1 -> {
                                Utils.Info("更新结果:"+ohAttributes1.get("mana").getValue());
                            }
                    );
                }
            });
        }

    }








}
