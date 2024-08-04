package com.isl.outland_horizon.potion;

import com.isl.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potions;
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
            entity.hurt(new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
