package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.core.ModDataManager;
import com.arc.outland_horizon.registry.OHMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract boolean hurt(DamageSource pSource, float pAmount);

    @Unique
    public abstract boolean outland_horizon$hasEffect(MobEffect pEffect);

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"))
    private void aiStepMixin(Player instance, float v) {
        float value = v;
        if (this.outland_horizon$hasEffect(OHMobEffects.BLEED.get())) {
            value = 0;
        } else {
            switch (ModDataManager.modDifficulties) {
                case DEATH -> value = value * 2 / 3;
                case TRIBULATION -> value = value / 2;
                case ETERNAL -> value = value / 3;
            }
        }
        instance.heal(value);
    }
}
