package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.ModDifficulties;
import com.arc.outland_horizon.OHDataManager;
import com.arc.outland_horizon.network.NetworkHandler;
import com.arc.outland_horizon.network.OHChangeDifficultyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(OptionsScreen.class)
public abstract class OptionScreenMixin {
    @Unique
    private static CycleButton<ModDifficulties> outland_horizon$btn;

    @Inject(method = "createDifficultyButton", at = @At(value = "HEAD"), cancellable = true)
    private static void b(int pX, int pY, String pTranslationKey, Minecraft mc, CallbackInfoReturnable<CycleButton<Difficulty>> cir) {
        cir.setReturnValue(CycleButton.builder(Difficulty::getDisplayName).withValues(Difficulty.values()).withInitialValue(mc.level.getDifficulty()).create(pX, pY, 150, 20, Component.translatable(pTranslationKey), (p_193854_, difficulty) -> {
            if (!(difficulty == Difficulty.HARD)) {
                outland_horizon$btn.active = false;
                NetworkHandler.sendToServer(new OHChangeDifficultyPacket(ModDifficulties.DISABLED));
            } else {
                outland_horizon$btn.active = true;
                NetworkHandler.sendToServer(new OHChangeDifficultyPacket(outland_horizon$btn.getValue()));
            }
            mc.getConnection().send(new ServerboundChangeDifficultyPacket(difficulty));
        }));
    }

    @Unique
    private CycleButton<ModDifficulties> outland_horizon$btn(int x, int y, int w, int h, Minecraft mc) {
        CycleButton<ModDifficulties> button = CycleButton.builder(ModDifficulties::getDisplayName)
                .withValues(ModDifficulties.values())
                .withInitialValue(OHDataManager.modDifficulties)
                .create(x, y, w, h, Component.translatable("options.oh_difficulty"), (cycleButton, difficulties) -> {
                    cycleButton.setTooltip(Tooltip.create(cycleButton.getValue().getInfo()));
                    NetworkHandler.sendToServer(new OHChangeDifficultyPacket(outland_horizon$btn.getValue()));
                });
        if (mc.level != null) {
            button.active = mc.level.getDifficulty() == Difficulty.HARD;
        }
        button.setTooltip(Tooltip.create(button.getValue().getInfo()));
        return button;
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/SpacerElement;height(I)Lnet/minecraft/client/gui/layouts/SpacerElement;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void a(CallbackInfo ci, GridLayout gridlayout, GridLayout.RowHelper gridlayout$rowhelper) {
        if (Minecraft.getInstance().level != null) {
            outland_horizon$btn = outland_horizon$btn(0, 0, 150, 20, Minecraft.getInstance());
            gridlayout$rowhelper.addChild(SpacerElement.height(26));
            gridlayout$rowhelper.addChild(outland_horizon$btn);
        }
    }
}
