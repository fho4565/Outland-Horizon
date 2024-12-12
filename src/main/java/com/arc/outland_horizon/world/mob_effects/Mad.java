package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Mad extends MobEffect {
    public static final UUID ARMOR_UUID = UUID.fromString("41d73c12-fcdf-36e0-b17b-ec06e5c76e5e");
    public static final UUID ATTACKDAMAGE_UUID = UUID.fromString("52acad3c-a357-32ce-a42a-a7b530a4a232");
    public static final UUID ATTACKSPEED_UUID = UUID.fromString("bec68b34-8280-3c12-984d-3091d66de3b2");

    public Mad() {
        super(MobEffectCategory.NEUTRAL, 16733525);

    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }

    @Override
    public void addAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID, "outland_horizon.mob_effect.mad.armor", -(amplifier + 1) * 2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, "outland_horizon.mob_effect.mad.attack_damage", (amplifier + 1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID, "outland_horizon.mob_effect.mad.attack_damage", (amplifier + 1) * 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).addPermanentModifier(attackDamage);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_SPEED)).addPermanentModifier(attackSpeed);
    }

    @Override
    public void removeAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ARMOR_UUID, "outland_horizon.mob_effect.mad.armor", -(amplifier + 1) * 2, AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, "outland_horizon.mob_effect.mad.attack_damage", (amplifier + 1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeed = new AttributeModifier(ATTACKSPEED_UUID, "outland_horizon.mob_effect.mad.attack_damage", (amplifier + 1) * 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ARMOR)).removeModifier(armor);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).removeModifier(attackDamage);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_SPEED)).removeModifier(attackSpeed);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
