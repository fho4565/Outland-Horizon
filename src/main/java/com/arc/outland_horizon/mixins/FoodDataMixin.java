package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.OHDataManager;
import com.arc.outland_horizon.registry.OHMobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"))
    public void fixHealth(Player instance, float v) {
        if (!instance.hasEffect(OHMobEffects.BLEED.get())) {
            switch (OHDataManager.modDifficulties) {
                case DEATH -> instance.heal(v * 2 / 3);
                case TRIBULATION -> instance.heal(v / 2);
                case ETERNAL -> instance.heal(v / 3);
                default -> instance.heal(v);
            }
        }
    }
}
