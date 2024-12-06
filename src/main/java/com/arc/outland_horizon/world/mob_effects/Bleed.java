package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Bleed extends MobEffect {

    public Bleed() {
        super(MobEffectCategory.HARMFUL, -65536);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
