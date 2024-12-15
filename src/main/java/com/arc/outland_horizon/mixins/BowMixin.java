package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.world.item.ornaments.AbstractOrnaments;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowMixin {
    @Inject(method = "getPowerForTime", at = @At("RETURN"), cancellable = true)
    private static void a(int pCharge, CallbackInfoReturnable<Float> cir) {
        if (EntityUtils.getAllCurios(Minecraft.getInstance().player).stream().anyMatch(itemStack -> itemStack.getItem() instanceof AbstractOrnaments)) {
            cir.setReturnValue(Math.min(cir.getReturnValue() * 1.6f, 1.0f));
        }
    }
}
