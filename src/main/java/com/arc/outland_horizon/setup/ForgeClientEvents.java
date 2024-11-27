package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.network.NetworkHandler;
import com.arc.outland_horizon.network.server.C2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.arc.outland_horizon.client.key.KeyRegistry.KEY_RAGE;
import static com.arc.outland_horizon.registry.OHBlocks.Fluid.FluidRegistry.BLOOD;
import static com.arc.outland_horizon.registry.OHBlocks.Fluid.FluidRegistry.BLOOD_FLOWING;

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
    public static void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BLOOD.get(), RenderType.waterMask());
        ItemBlockRenderTypes.setRenderLayer(BLOOD_FLOWING.get(), RenderType.waterMask());
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            LocalPlayer player = Minecraft.getInstance().player;
            while (KEY_RAGE.consumeClick()) {
                if (player != null) {
                    NetworkHandler.sendToServer(new C2SPacket(C2SPacket.Operation.TRIGGER_RAGE));
                }
            }
        }
    }
}
