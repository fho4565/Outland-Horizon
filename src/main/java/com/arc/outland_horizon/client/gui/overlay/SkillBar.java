package com.arc.outland_horizon.client.gui.overlay;

import com.arc.outland_horizon.world.item.ISkillItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SkillBar extends HudSection {

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
        int x = guiGraphics.guiWidth() / 2 - 20;
        int y = guiGraphics.guiHeight() / 2 + 30;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 0);
        guiGraphics.setColor(1F, 1F, 1F, 1F);
        if (itemStack.getItem() instanceof ISkillItem skillItem) {
            if (skillItem.shouldRenderSkillCooldownBar(itemStack)) {
                if (skillItem.isCurrentSkillCooldown(itemStack)) {
                    int barWidth = skillItem.skillCooldownBarWidth(itemStack);
                    int barColor = skillItem.skillBarColor(itemStack);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 45, y + 3, -16777216);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + barWidth, y + 3, barColor | -16777216);
                }
            }
            if (skillItem.shouldRenderSkillDurationBar(itemStack)) {
                guiGraphics.pose().pushPose();
                if (skillItem.isCurrentSkillActive(itemStack)) {
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
