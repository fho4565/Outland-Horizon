package com.isl.outland_horizon.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class Bleed extends MobEffect {
    private int hurtCoolDown = 0;

    public Bleed() {
        super(MobEffectCategory.HARMFUL, -65536);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (++hurtCoolDown >= 20) {
            hurtCoolDown = 0;
            entity.hurt(entity.level().damageSources().generic(), 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
