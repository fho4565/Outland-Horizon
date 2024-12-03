package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.world.item.ICooldownItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Unique
    private static final int BAR_LENGTH = 15;
    @Shadow
    @Final
    private PoseStack pose;

    @Shadow
    public abstract void fill(RenderType pRenderType, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor);

    @Inject(at = @At("HEAD"), method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V")
    public void renderItemDecorationsMixin(Font font, ItemStack itemStack, int x, int y, String text, CallbackInfo ci) {
        if (!itemStack.isEmpty()) {
            if (itemStack.getItem() instanceof ICooldownItem cooldownItem) {
                if (cooldownItem.shouldRenderCooldownBar(itemStack)) {
                    this.pose.pushPose();
                    if (cooldownItem.isCooldown(itemStack) || cooldownItem.renderCooldownBarWhenEnds(itemStack)) {
                        int barWidth = cooldownItem.cooldownBarWidth(itemStack);
                        int barColor = cooldownItem.cooldownBarColor(itemStack);
                        this.fill(RenderType.guiOverlay(), x, y, x + 1, y + BAR_LENGTH, -16777216);
                        this.fill(RenderType.guiOverlay(), x, y + BAR_LENGTH, x + 1, y + BAR_LENGTH - barWidth, barColor | -16777216);
                    }
                    this.pose.popPose();
                }
            }

        }
    }
}
