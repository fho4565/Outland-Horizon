package com.isl.outland_horizon.event;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.level.capa.data.OhAttribute;
import com.isl.outland_horizon.network.PacketHandler;
import com.isl.outland_horizon.network.s2c.AttributesToClientPacket;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player ){
            player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(ohAttributes -> {
                ArrayList<OhAttribute.ScapeApi> needSyncList = new ArrayList<>(ohAttributes.getProfession().values());
                PacketHandler.sendToPlayer(new AttributesToClientPacket(needSyncList, player.getId()),player);
            });
        }
    }
    @SubscribeEvent
    public static void onTickPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.level().random.nextDouble()>0.5f && event.phase== TickEvent.Phase.END) {
            if (!player.level().isClientSide) {
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(
                        Attributes -> {
                            Attributes.setAttrValue("mana", (Double) Attributes.getAttrValue("mana") + 0.1);
                            Utils.sendSimpleMessageToPlayer(player, "[服务端]你当前的魔力值:" + Attributes.get("mana").getValue());
                        }
                );
            }
            if (Minecraft.getInstance().player != null) {//客户端 [记住客户端每次重启能力都会消失：如果正常这里会将服务端的数据同步成初始数值]
                Minecraft.getInstance().player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(ohAttributes -> {
                    Utils.sendSimpleMessageToPlayer(player, "[客户端]你当前的魔力值:" + ohAttributes.get("mana").getValue());
                });
            }
        }
    }










}
