package com.arc.outland_horizon.client.gui.overlay;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.HashSet;

public class PlayerOverlay implements IGuiOverlay {
    public static final HashSet<HudSection> hudSections = new HashSet<>();

    public PlayerOverlay() {
        hudSections.add(new HealthBar());
        hudSections.add(new ManaBar());
        hudSections.add(new SkillBar());
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        hudSections.forEach(hudSection -> hudSection.render(gui.getMinecraft(), guiGraphics));
    }
}
