package com.arc.outland_horizon.world.block.fluids.blood;


import com.arc.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class ArterialBloodType extends FluidType {
    public ArterialBloodType() {
        super(Properties.create()
                .fallDistanceModifier(0F)
                .canExtinguish(true)
                .supportsBoating(false)
                .motionScale(0.01D)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = new ResourceLocation(Utils.MOD_ID+":block/arterial_blood")
                    , FLOWING_TEXTURE = new ResourceLocation(Utils.MOD_ID+":block/arterial_blood");

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