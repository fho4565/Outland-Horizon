package com.isl.outland_horizon.event;

import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.level.capa.data.OhAttribute;
import com.isl.outland_horizon.network.PacketHandler;
import com.isl.outland_horizon.network.c2s.CapaToServerPacker;
import com.isl.outland_horizon.network.s2c.AttributesToClientPacket;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class CapabilitiesEvent {
    //将注册的能力添加到某个实体这里添加的是玩家
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).isPresent()) {
                event.addCapability(new ResourceLocation(Utils.MOD_ID, "profession"), new OhCapaHandler());
            }
        }
    }
    //初始化能力类
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(OhCapaHandler.class);
    }

    //从旧实体拷贝能力
    @SubscribeEvent
    public static void endFix(PlayerEvent.Clone event)
    {
        if (!event.isWasDeath() && !event.getEntity().level().isClientSide)//切换维度
        {
            Player oldPlayer = event.getOriginal();
            oldPlayer.reviveCaps();
            event.getEntity().getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(cap ->
                    oldPlayer.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(cap::copy));

            oldPlayer.invalidateCaps();
        }else if (!event.getEntity().level().isClientSide){//死亡
            Player oldPlayer = event.getOriginal();
            oldPlayer.reviveCaps();

            event.getEntity().getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(cap ->
                    oldPlayer.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(cap::copy));
            oldPlayer.invalidateCaps();

        }
    }

    @SubscribeEvent
    public static void playerServerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) {
                //客户端检测是否向服务端发送同步
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent((capability -> {
                    if (capability.isNeedSync()) {
                        //send to server
                        List<OhAttribute.ScapeApi> needSyncList = capability.getProfession().values().stream()
                                .filter(OhAttribute.ScapeApi::getSync)
                                .peek(obj -> obj.setSync(false))
                                .collect(Collectors.toCollection(ArrayList::new));
                        PacketHandler.simpleChannel.sendToServer(new CapaToServerPacker(needSyncList));
                        Utils.Info("同步开始："+capability.isNeedSync());
                        capability.setNeedSync(false);
                    }
                }));

            } else {
                //服务端检测是否要向其它玩家广播同步
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent((capability -> {
                    if (capability.isNeedSync()) {
                        //收集需要广播的属性
                        List<OhAttribute.ScapeApi> needSyncList = capability.getProfession().values().stream()
                                .filter(attribute -> attribute.getSync() && !attribute.getC2S())
                                .peek(obj -> obj.setSync(false))
                                .collect(Collectors.toCollection(ArrayList::new));
                        PacketHandler.simpleChannel.send(PacketDistributor.TRACKING_ENTITY.with(() -> event.player), new AttributesToClientPacket(needSyncList, player.getId()));



                        Utils.Info("游戏段更新完成");
                        capability.setNeedSync(false);

                    }
                }));
            }
        }
    }


}
