package com.isl.outland_horizon.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public abstract class HudSection {
    public int x;
    public int y;
    public HudSection(int x, int y){
        this.x = x;
        this.y = y;
    }
    public abstract void render(Minecraft minecraft, GuiGraphics guiGraphics);
}
