package com.isl.outland_horizon.event;

import com.isl.outland_horizon.Utils;
import com.isl.outland_horizon.level.capa.OhCapaHandler;
import com.isl.outland_horizon.level.capa.data.OhAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
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
    public static void playerServerTick(TickEvent.PlayerTickEvent event) {//玩家服务端tick
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) { //客户端,根据某个条件获取是否应该进行同步
                player.getCapability(OhCapaHandler.PLAYER_ATTRIBUTE).ifPresent(scapeAttributes -> {
                   if (!scapeAttributes.getProfession().isEmpty()){
                       ArrayList<OhAttribute.ScapeApi> needSyncList = scapeAttributes.getProfession().values().stream()
                               .filter(OhAttribute.ScapeApi::getSync)
                               .collect(Collectors.toCollection(ArrayList::new));

                       //这里需要进行一个操作将数据传到服务端
                      needSyncList.forEach(scapeApi -> {
                               //需要同步的数据
                      });
                   }

                });
            }else {//服务端,服务端向客户端下发数据逻辑,可以写在network 去实现并非一定得在这





            }
        }
    }


}
