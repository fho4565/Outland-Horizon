package com.arc.outland_horizon.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

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
        int offsetX = 0,offsetY = 0;
        if(scale<=0.05f){
            RandomSource randomSource = minecraft.player.getRandom();
            offsetX = Math.round((randomSource.nextFloat() - 0.5f) * 2);
            offsetY = Math.round((randomSource.nextFloat() - 0.5f) * 2);
        }
        guiGraphics.blit(texture, offsetX, offsetY,
                1, 0,0,
                140, 20,
                140, 21);
        guiGraphics.fill(22 + offsetX, 5 + offsetY,
                Math.round(22 + offsetX + 111 * scale), 5 + offsetY + 11,
                0,
                Color.RED.getRGB());
    }
}
