package com.arc.outland_horizon.client.gui.overlay;

import com.arc.outland_horizon.world.item.ISkillItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SkillBar extends HudSection {
    public static final int MAX_BAR_WIDTH = 158;
    private static final int BAR_LENGTH = 45;

    public SkillBar() {
        super(0, 0);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        ItemStack itemStack = new ItemStack(Items.AIR);
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            itemStack = player.getMainHandItem();
        }
        int x = guiGraphics.guiWidth() / 2 - 60;
        int y = guiGraphics.guiHeight() / 2 - 30;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 0);
        guiGraphics.setColor(1F, 1F, 1F, 1F);
        if (itemStack.getItem() instanceof ISkillItem skillItem) {
            if (skillItem.shouldRenderSkillCooldownBar(itemStack)) {
                if (skillItem.isInCooldown(itemStack)) {
                    int barWidth = skillItem.skillCooldownBarWidth(itemStack);
                    int barColor = skillItem.skillBarColor(itemStack);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 45, y + 3, -16777216);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + barWidth, y + 3, barColor | -16777216);
                }
            }
            if (skillItem.shouldRenderSkillDurationBar(itemStack)) {
                guiGraphics.pose().pushPose();
                if (skillItem.isInActive(itemStack)) {
                    int barWidth = skillItem.skillDurationBarWidth(itemStack);
                    int barColor = skillItem.skillBarColor(itemStack);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 45, y + 3, -16777216);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + barWidth, y + 3, barColor | -16777216);
                }
            }
        }
        guiGraphics.pose().popPose();
    }
}
