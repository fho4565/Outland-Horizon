package com.arc.outland_horizon.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public abstract class HudSection {
    public HudSection() {
    }

    public abstract void render(Minecraft minecraft, GuiGraphics guiGraphics);
}
