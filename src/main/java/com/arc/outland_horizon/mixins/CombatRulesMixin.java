package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.core.Combat;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CombatRules.class)
public class CombatRulesMixin {
    /**
     * @author fho4565
     * @reason 模组护甲值减伤调整
     */
    @Overwrite
    public static float getDamageAfterAbsorb(float pDamage, float pTotalArmor, float pToughnessAttribute) {
        return Combat.calculateDamageWithArmor(pDamage, pTotalArmor, pToughnessAttribute);
    }
}
