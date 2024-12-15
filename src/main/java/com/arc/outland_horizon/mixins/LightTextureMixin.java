package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.registry.OHMobEffects;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LightTexture.class)
public class LightTextureMixin {
    @Redirect(method = "updateLightTexture",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getWaterVision()F"), to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    )
    public boolean effect(LocalPlayer instance, MobEffect mobEffect) {
        return instance.hasEffect(MobEffects.NIGHT_VISION) || instance.hasEffect(OHMobEffects.OH_NIGHT_VISION.get());
    }
}
