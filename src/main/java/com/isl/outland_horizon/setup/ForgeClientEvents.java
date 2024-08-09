package com.isl.outland_horizon.setup;

import com.isl.outland_horizon.utils.Utils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.isl.outland_horizon.world.block.BlockRegistry.FluidRegistry.BLOOD;
import static com.isl.outland_horizon.world.block.BlockRegistry.FluidRegistry.BLOOD_FLOWING;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
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
        ItemBlockRenderTypes.setRenderLayer(BLOOD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BLOOD_FLOWING.get(), RenderType.translucent());
    }
}
