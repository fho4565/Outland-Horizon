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
        guiGraphics.blit(texture, 200, 100, 0, 0, minecraft.getWindow().getScreenWidth()/25, minecraft.getWindow().getScreenHeight()/100, minecraft.getWindow().getScreenWidth()/25, minecraft.getWindow().getScreenHeight()/100);
        guiGraphics.fill(x, y, Math.round(x + (minecraft.getWindow().getScreenWidth() / 25.0f) * scale), y + minecraft.getWindow().getScreenHeight()/100, Color.RED.getRGB());
    }
}
