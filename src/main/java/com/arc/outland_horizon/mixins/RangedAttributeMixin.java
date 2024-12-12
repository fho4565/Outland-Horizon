package com.arc.outland_horizon.mixins;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedAttribute.class)
public class RangedAttributeMixin {
    @Mutable
    @Shadow
    @Final
    private double maxValue;

    @Inject(method = "sanitizeValue", at = @At("HEAD"))
    public void fix(double pValue, CallbackInfoReturnable<Double> cir) {
        this.maxValue = Double.MAX_VALUE;
    }
}
