package com.isl.outland_horizon.event;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
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
                            Utils.sendSimpleMessageToPlayer(player, "[加载]你当前的魔力值:" + Attributes.get("mana").getValue());
                        }
                );
            }
        }
    }


    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity EntitySource = event.getSource().getEntity();
        if (EntitySource instanceof Player player ) {
            if (!player.level().isClientSide){//用于检查服务端数据
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(
                        Attributes -> {
                            Utils.sendSimpleMessageToPlayer(player, "[数据探测]你当前的魔力值:" + Attributes.get("mana").getValue());
                        if (event.getEntity() instanceof Monster){//如果是怪物服务端数据更新
                            Attributes.setAttrValue("mana", (Double) Attributes.getAttrValue("mana") + 0.5);
                            Utils.sendSimpleMessageToPlayer(player, "[服务端]你当前的魔力值:" + Attributes.get("mana").getValue());
                        }
                        }
                );
            }
            if (Minecraft.getInstance().player != null) {//客户端 [记住客户端每次重启能力都会消失：如果正常这里会将服务端的数据同步成初始数值]
                Minecraft.getInstance().player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(ohAttributes -> {
                    ohAttributes.setAttrValue("mana", (Double) ohAttributes.getAttrValue("mana") + 0.5);
                    Utils.sendSimpleMessageToPlayer(player, "[客户端]你当前的魔力值:" + ohAttributes.get("mana").getValue());
                });
            }



        }

    }








}
