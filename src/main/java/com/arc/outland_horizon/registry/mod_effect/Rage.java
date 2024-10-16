package com.arc.outland_horizon.registry.mod_effect;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class Rage extends MobEffect {
    public static final UUID ATTACKDAMAGE_UUID = Utils.generateUUIDFromText(Utils.MOD_ID+".mob_effect.rage.attack_damage");

    public Rage() {
        super(MobEffectCategory.NEUTRAL, 11141120);
    }
    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID,Utils.MOD_ID+".mob_effect.rage.attack_damage", (amplifier+1), AttributeModifier.Operation.ADDITION);
        attributeMap.getInstance(Attributes.ATTACK_DAMAGE).addPermanentModifier(attackDamage);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID,Utils.MOD_ID+".mob_effect.rage.attack_damage", (amplifier+1), AttributeModifier.Operation.ADDITION);
        attributeMap.getInstance(Attributes.ATTACK_DAMAGE).removeModifier(attackDamage);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public MobEffectCategory getCategory() {
        return MobEffectCategory.BENEFICIAL;
    }

}
