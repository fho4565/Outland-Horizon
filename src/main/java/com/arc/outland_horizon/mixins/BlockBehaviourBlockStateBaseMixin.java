package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.registry.OHDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockBehaviourBlockStateBaseMixin {

    @Inject(method = "getLightEmission", at = @At("RETURN"), cancellable = true)
    public void lightFix(CallbackInfoReturnable<Integer> cir) {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.dimension().compareTo(OHDimensions.DYSTOPIA) == 0) {
            cir.setReturnValue(0);
        }
    }
}
