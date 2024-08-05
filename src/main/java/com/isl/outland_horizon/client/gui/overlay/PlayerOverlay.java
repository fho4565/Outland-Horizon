package com.isl.outland_horizon.client.gui.overlay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;

public class PlayerOverlay implements IGuiOverlay {
    public static HashSet<HudSection> hudSections = new HashSet<>();

    public PlayerOverlay() {
        hudSections.add(new HealthBar());
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        hudSections.forEach(hudSection -> hudSection.render(gui.getMinecraft(), guiGraphics));
    }
    @SubscribeEvent
    public void onGameOverlayRenderPre(RenderGuiOverlayEvent.Pre event) {
        System.out.println("|||||||||||||||||||||||||||||||||||||");
        ResourceLocation overlay = event.getOverlay().id();
        if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.id()) {
            event.setCanceled(true);
        }
    }
}
