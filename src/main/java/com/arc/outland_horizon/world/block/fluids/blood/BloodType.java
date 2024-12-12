package com.arc.outland_horizon.world.block.fluids.blood;


import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.awt.*;
import java.util.function.Consumer;

public class BloodType extends FluidType {
    public BloodType() {
        super(FluidType.Properties.create().fallDistanceModifier(0F)
                .canExtinguish(true)
                .supportsBoating(false)
                .motionScale(0.004D)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = new ResourceLocation(OutlandHorizon.MOD_ID + ":block/fluid/blood_still"), FLOWING_TEXTURE = new ResourceLocation(OutlandHorizon.MOD_ID + ":block/fluid/blood");

            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return OutlandHorizon.createModResourceLocation("textures/block/fluid/blood_overlay.png");
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                Color red = Color.RED;
                return new Vector3f(red.getRed() / 255.0f, red.getGreen() / 255.0f, red.getBlue() / 255.0f);
            }

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }
        });
    }
}