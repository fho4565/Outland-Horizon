package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Iron extends MobEffect {
    public static final UUID ID = UUID.fromString("c63191c8-8799-3c1d-b8cc-5ff101c60ba7");

    public Iron() {
        super(MobEffectCategory.NEUTRAL, new Color(0x858585).getRGB());
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ID, "outland_horizon.mob_effect.iron", 2 + amplifier, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ARMOR)).addPermanentModifier(armor);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        AttributeModifier armor = new AttributeModifier(ID, "outland_horizon.mob_effect.iron", 2 + amplifier, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(attributeMap.getInstance(Attributes.ATTACK_DAMAGE)).removeModifier(armor);
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
