package com.isl.outland_horizon.world.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class Nightmare {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class UjSpecialEffectsHandler {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
            DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false) {
                @Override
                public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                    return new Vec3(1, 0, 0);
                }

                @Override
                public boolean isFoggyAt(int x, int y) {
                    return true;
                }
                @Override
                public float @Nullable [] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
                    float[] clearColor = new float[4];

                    clearColor[0] = 0.0f;
                    clearColor[1] = 0.0f;
                    clearColor[2] = 0.0f;
                    clearColor[3] = 0.0f;
                    return clearColor;
                }
            };
            event.register(new ResourceLocation("outland_horizon:nightmare"), customEffect);
        }
    }
}

