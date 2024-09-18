package com.isl.outland_horizon.world.mod_effect;

import com.isl.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class Rage extends MobEffect {
    public static final UUID ARMOR_UUID = UUID.fromString("31a59898-f99e-483a-959e-893445f3dd13");
    public static final UUID ATTACKDAMAGE_UUID = UUID.fromString("1931554c-eddc-432c-989b-962f79d747ee");
    public static final UUID ATTACKSPEED_UUID = UUID.fromString("62182276-2246-4441-99fd-584ca3c23129");

    public Rage() {
        super(MobEffectCategory.NEUTRAL, 16733525);

    }
    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID,Utils.MOD_ID+".mob_effect.rage", -(amplifier+1)*2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID,Utils.MOD_ID+".mob_effect.attack_damage", (amplifier+1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID,Utils.MOD_ID+".mob_effect.attack_damage", (amplifier+1)*0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        attributeMap.getInstance(Attributes.ARMOR).addPermanentModifier(armor);
        attributeMap.getInstance(Attributes.ATTACK_DAMAGE).addPermanentModifier(attackDamage);
        attributeMap.getInstance(Attributes.ATTACK_SPEED).addPermanentModifier(attackSpeed);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID,Utils.MOD_ID+".mob_effect.rage", -(amplifier+1)*2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID,Utils.MOD_ID+".mob_effect.attack_damage", (amplifier+1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID,Utils.MOD_ID+".mob_effect.attack_damage", (amplifier+1)*0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        attributeMap.getInstance(Attributes.ARMOR).removeModifier(armor);
        attributeMap.getInstance(Attributes.ATTACK_DAMAGE).removeModifier(attackDamage);
        attributeMap.getInstance(Attributes.ATTACK_SPEED).removeModifier(attackSpeed);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
