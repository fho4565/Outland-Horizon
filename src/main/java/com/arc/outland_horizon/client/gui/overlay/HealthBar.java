package com.arc.outland_horizon.client.gui.overlay;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.awt.*;

public class HealthBar extends HudSection {
    public static final int MAX_BAR_WIDTH = 158;
    private final ResourceLocation texture = OutlandHorizon.createModResourceLocation("textures/gui/health_bar.png");

    public HealthBar() {
        super(0, 0);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 0);
        guiGraphics.setColor(1F, 1F, 1F, 1F);
        LocalPlayer player = minecraft.player;
        if (player != null) {
            float healthScale = player.getHealth() / player.getMaxHealth();
            float maxRenderAbsorptionAmount = player.getAbsorptionAmount();
            if (maxRenderAbsorptionAmount > player.getMaxHealth()) {
                maxRenderAbsorptionAmount = player.getMaxHealth();
            }
            float absorptionScale = maxRenderAbsorptionAmount / player.getMaxHealth();
            float maxRenderShieldValueAmount = (float) OhAttributeProvider.shieldValue;
            if (maxRenderShieldValueAmount > player.getMaxHealth()) {
                maxRenderShieldValueAmount = player.getMaxHealth();
            }
            float shieldValueScale = maxRenderShieldValueAmount / player.getMaxHealth();
            int offsetX = 0, offsetY = 0;
            if (healthScale <= 0.05f) {
                RandomSource randomSource = null;
                if (minecraft.player != null) {
                    randomSource = minecraft.player.getRandom();
                }
                if (randomSource != null) {
                    offsetX = Math.round((randomSource.nextFloat() - 0.5f) * 2);
                    offsetY = Math.round((randomSource.nextFloat() - 0.5f) * 2);
                }
            }
            guiGraphics.blit(texture, offsetX, offsetY,
                    0, 0,
                    160, 48,
                    160, 48);
            String hpString = String.format("%.1f/%.1f", player.getHealth(), player.getMaxHealth());
            guiGraphics.drawString(minecraft.font, hpString, (2 + offsetX + minecraft.font.width(hpString)), (2 + offsetY + minecraft.font.lineHeight) / 2, 100, false);
            renderHealthBar(minecraft, guiGraphics, player, offsetX, offsetY);
            String shieldString = String.format("%.1f", OhAttributeProvider.shieldValue);
            guiGraphics.drawString(minecraft.font, shieldString, 160 + offsetX, (28 + offsetY + minecraft.font.lineHeight) / 2, 100, false);

            guiGraphics.fill(
                    2 + offsetX,
                    19 + offsetY,
                    Math.round(MAX_BAR_WIDTH * shieldValueScale) + offsetX,
                    22 + offsetY,
                    -1,
                    Color.GRAY.getRGB());
        }
        guiGraphics.pose().popPose();
    }

    private void renderHealthBar(Minecraft mc, GuiGraphics guiGraphics, LocalPlayer player, int offsetX, int offsetY) {
        float maxHealth = player.getMaxHealth();
        float health = player.getHealth();
        if (player.getAbsorptionAmount() > 0) {
            float absorptionAmount = player.getAbsorptionAmount();
            float barWidth = (health + absorptionAmount) / maxHealth * MAX_BAR_WIDTH;
            if (barWidth > MAX_BAR_WIDTH) {
                barWidth = MAX_BAR_WIDTH;
            }
            float absorptionBarWidth = absorptionAmount / (health + absorptionAmount) * barWidth;
            float healthBarWidth = health / (health + absorptionAmount) * barWidth;
            guiGraphics.fill(
                    2 + offsetX,
                    2 + offsetY,
                    Math.round(healthBarWidth) + offsetX,
                    14 + offsetY,
                    -1,
                    Color.RED.darker().getRGB());
            guiGraphics.fill(
                    Math.round(2 + healthBarWidth + 1 + offsetX),
                    2 + offsetY,
                    Math.round(absorptionBarWidth) + offsetX,
                    14 + offsetY,
                    -1,
                    Color.YELLOW.getRGB());
        } else {
            float barWidth = health / maxHealth * MAX_BAR_WIDTH;
            guiGraphics.fill(
                    2 + offsetX,
                    2 + offsetY,
                    Math.round(barWidth) + offsetX,
                    14 + offsetY,
                    -1,
                    Color.RED.darker().getRGB());
        }
    }
}
