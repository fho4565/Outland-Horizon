package com.isl.outland_horizon.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class HealthBar extends HudSection{
    private final ResourceLocation texture = new ResourceLocation("outland_horizon", "textures/gui/health_bar.png");
    public HealthBar() {
        super(0, 0);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        float scale = player.getHealth()/player.getMaxHealth();
        guiGraphics.blit(texture, 0, 0,
                1, 0,1,
                140, 21,
                140, 21);
        guiGraphics.fill(22, 4,
                Math.round(22 + 111 * scale),
                4 + 11,0,Color.RED.getRGB());
    }
}
