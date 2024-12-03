package com.arc.outland_horizon.world.item.weapons.melee.sickle;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.world.Skill;
import com.arc.outland_horizon.world.item.ISkillItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class ZenithCrimsonDemise extends TieredItem implements ISkillItem {
    final Skill skill1 = new Skill(Component.literal("a"),
            Component.literal("b"),
            0, 200, 100
    ) {
        @Override
        public void onSkillStart(Player player, ItemStack itemStack) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 5, false, false));
        }
    };

    public ZenithCrimsonDemise() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 452;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 10;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(OHItems.Material.NIGHTMARE_ENERGY.get());
            }
        }, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        if (isInActive(itemStack)) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20, 30));
        }
        return super.hurtEnemy(itemStack, target, attacker);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pSlot) {
        ImmutableMultimap<Attribute, AttributeModifier> defaultModifiers;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 13, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.9, AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
        return defaultModifiers;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (!validateTags(stack)) {
            initSkills(stack);
        }
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public Skill skill1() {
        return skill1;
    }
}
