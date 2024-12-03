package com.arc.outland_horizon.world.mob_effects;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class Mad extends MobEffect {
    public static final UUID ARMOR_UUID = Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + ".mob_effect.mad.armor");
    public static final UUID ATTACKDAMAGE_UUID = Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_damage");
    public static final UUID ATTACKSPEED_UUID = Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_speed");

    public Mad() {
        super(MobEffectCategory.NEUTRAL, 16733525);

    }

    @Override
    public void addAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.armor", -(amplifier + 1) * 2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_damage", (amplifier + 1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_damage", (amplifier + 1) * 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).addPermanentModifier(attackDamage);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_SPEED)).addPermanentModifier(attackSpeed);
    }

    @Override
    public void removeAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.armor", -(amplifier + 1) * 2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_damage", (amplifier + 1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID, OutlandHorizon.MOD_ID + ".mob_effect.mad.attack_damage", (amplifier + 1) * 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ARMOR)).removeModifier(armor);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).removeModifier(attackDamage);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_SPEED)).removeModifier(attackSpeed);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
