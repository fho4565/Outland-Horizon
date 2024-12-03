package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.ChatUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DevelopEvents {
    public static boolean isDebug = false;

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        if (isDebug) {
            LivingEntity entity = event.getEntity();
            MutableComponent entityName = Component.literal(entity.getDisplayName().getString());
            MutableComponent entityNameToolTip = Component.literal("UUID：" + entity.getUUID())
                    .append(Component.literal("坐标：" + entity.position()));
            entityName.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, entityNameToolTip)));
            ChatUtils.allPlayers(entityName.append("受到了" + event.getAmount() + "点伤害"));
        }
    }
}
