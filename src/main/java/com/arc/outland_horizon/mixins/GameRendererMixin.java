package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.registry.OHMobEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void modNightVision(LivingEntity pLivingEntity, float pNanoTime, CallbackInfoReturnable<Float> cir) {
        if (pLivingEntity.hasEffect(OHMobEffects.OH_NIGHT_VISION.get())) {
            cir.setReturnValue(1.0f);
        }
    }
}
