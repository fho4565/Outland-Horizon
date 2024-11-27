package com.arc.outland_horizon.mixins;

import net.minecraft.world.item.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BowItem.class)
public class BowMixin {
    /**
     * @author fho4565
     * @reason 修改原版
     */
    @Overwrite
    public static float getPowerForTime(int pCharge) {
        return 1.0f;
    }
}
