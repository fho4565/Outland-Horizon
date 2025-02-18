package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.network.NetworkHandler;
import com.arc.outland_horizon.network.packets.C2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.arc.outland_horizon.client.key.KeyRegistry.*;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onGameOverlayRenderPre(RenderGuiOverlayEvent.Pre event) {
        ResourceLocation overlay = event.getOverlay().id();
        if (VanillaGuiOverlay.PLAYER_HEALTH.id() == overlay) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            while (KEY_RAGE.consumeClick()) {
                NetworkHandler.sendToServer(new C2SPacket(C2SPacket.Operation.TRIGGER_RAGE));
            }
            while (KEY_TRIGGER_SKILL.consumeClick()) {
                NetworkHandler.sendToServer(new C2SPacket(C2SPacket.Operation.TRIGGER_SKILL));
            }
            while (KEY_SWITCH_SKILL.consumeClick()) {
                NetworkHandler.sendToServer(new C2SPacket(C2SPacket.Operation.SWITCH_SKILL));
            }
        }
    }
}
