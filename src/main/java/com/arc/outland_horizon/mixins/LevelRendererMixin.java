package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHDimensions;
import com.arc.outland_horizon.registry.ParticleRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    @Nullable
    private ClientLevel level;

    @Redirect(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
    public void rainTexture(int shaderTexture, ResourceLocation textureId) {
        if (level != null) {
            if (level.dimension().compareTo(OHDimensions.NIGHTMARE) == 0) {
                RenderSystem.setShaderTexture(0, OutlandHorizon.createModResourceLocation("textures/environment/nightmare_rain.png"));
            } else {
                RenderSystem.setShaderTexture(shaderTexture, textureId);
            }
        }
    }

    @Redirect(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void rainSplash(ClientLevel instance, ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        if (instance.dimension().compareTo(OHDimensions.NIGHTMARE) == 0) {
            instance.addParticle(ParticleRegistry.NIGHTMARE_RAIN_SPLASH.get(), pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        } else {
            instance.addParticle(pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        }
    }
}
