package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.core.Combat;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    protected abstract void hurtArmor(DamageSource pDamageSource, float pDamageAmount);

    @Shadow
    public abstract int getArmorValue();

    @Shadow
    public abstract double getAttributeValue(Attribute pAttribute);

    /**
     * @author fho4565
     * @reason 模组伤害调整
     */
    @Overwrite
    protected float getDamageAfterArmorAbsorb(DamageSource damageSource, float pDamageAmount) {
        Entity entity = damageSource.getEntity();
        if (entity instanceof Player player) {
            if (player.getMainHandItem().isEmpty()) {
                return pDamageAmount;
            }
        }
        if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            this.hurtArmor(damageSource, pDamageAmount);
            pDamageAmount = Combat.calculateDamageWithArmor(pDamageAmount, (float) this.getArmorValue(), (float) this.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }
        return pDamageAmount;
    }
}
