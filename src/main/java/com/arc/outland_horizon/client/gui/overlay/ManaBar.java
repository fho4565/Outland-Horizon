package com.arc.outland_horizon.client.gui.overlay;

import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ManaBar extends HudSection{
    public ManaBar() {
        super(0, 0);
    }
    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        LocalPlayer player = minecraft.player;
        if (player != null) {

            float scale = (float) (OhAttributeProvider.mana / OhAttributeProvider.maxMana);
            String text = ((int) OhAttributeProvider.mana) + " / " + ((int) OhAttributeProvider.maxMana);
            int width = minecraft.font.width(text);
            guiGraphics.drawString(minecraft.font,
                    Component.literal(text),
                    50-width/2, 80+minecraft.font.lineHeight/2, Color.WHITE.getRGB());
            guiGraphics.fill(0, 80, Math.round(100 * scale), 80+20, Color.BLUE.getRGB());
            guiGraphics.fill(0, 80, 100, 80+20, -1, Color.BLACK.getRGB());
        }
    }
}
