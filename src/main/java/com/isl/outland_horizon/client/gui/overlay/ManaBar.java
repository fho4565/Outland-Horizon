package com.isl.outland_horizon.client.gui.overlay;

import com.isl.outland_horizon.utils.ManaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

import java.awt.*;

public class ManaBar extends HudSection{
    public ManaBar() {
        super(0, 0);
    }
    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        float scale =  ((float)ManaUtils.getMana(player)/(float)ManaUtils.getMaxMana(player));

        guiGraphics.fill(0, 80, 160, 80+20, Color.BLACK.getRGB());
        guiGraphics.fill(0,80, Math.round(0+160*scale), 80+20,-1, Color.RED.getRGB());
    }
}
