package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Rage extends MobEffect {
    public static final UUID ATTACKDAMAGE_UUID = UUID.fromString("7134a7be-5ce7-35db-b464-5f272eb58ef3");

    public Rage() {
        super(MobEffectCategory.NEUTRAL, 11141120);
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, "outland_horizon.mob_effect.rage.attack_damage", (amplifier + 5) * 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).addPermanentModifier(attackDamage);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier attackDamage = new AttributeModifier(ATTACKDAMAGE_UUID, "outland_horizon.mob_effect.rage.attack_damage", (amplifier + 1) * 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
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
