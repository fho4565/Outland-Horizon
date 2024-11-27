package com.arc.outland_horizon.world.mob_effects;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class Rage extends MobEffect {
    public static final UUID ATTACKDAMAGE_UUID = Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + ".mob_effect.rage.attack_damage");

    public Rage() {
        super(MobEffectCategory.NEUTRAL, 11141120);
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, OutlandHorizon.MOD_ID + ".mob_effect.rage.attack_damage", (amplifier + 1) * 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).addPermanentModifier(attackDamage);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, OutlandHorizon.MOD_ID + ".mob_effect.rage.attack_damage", (amplifier + 1) * 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).removeModifier(attackDamage);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public @NotNull MobEffectCategory getCategory() {
        return MobEffectCategory.BENEFICIAL;
    }

}
