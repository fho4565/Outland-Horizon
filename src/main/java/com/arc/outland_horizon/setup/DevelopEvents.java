package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DevelopEvents {
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        if(Utils.isDevelopEnvironment()){
            LivingEntity entity = event.getEntity();
            ChatUtils.sendMessageToAllPlayers(entity.getDisplayName().getString() +"受到了"+event.getAmount()+"点伤害");
        }
    }
}
