package com.isl.outland_horizon.world.dimension;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber
public class Nightmare {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class NightmareSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false) {
                private final Minecraft minecraft = Minecraft.getInstance();
                private int rainSoundTime;
                @Override
                public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 color, float sunHeight) {
                    return new Vec3(1, 0, 0);
                }
                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }

                @Override
                public boolean renderClouds(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, double camX, double camY, double camZ, @NotNull Matrix4f projectionMatrix) {
                    return super.renderClouds(level, ticks, partialTick, poseStack, camX, camY, camZ, projectionMatrix);
                }

            };
            event.register(new ResourceLocation("outland_horizon:nightmare"), customEffect);
        }
    }
}

