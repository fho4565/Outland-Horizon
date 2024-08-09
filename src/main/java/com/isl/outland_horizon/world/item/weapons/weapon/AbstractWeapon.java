package com.isl.outland_horizon.world.item.weapons.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractWeapon extends TieredItem {
    protected final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AbstractWeapon(int maxDurability, int meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        super(new Tier() {
            public int getUses() {
                return maxDurability;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return meleeAttackDamage;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return enchantAbility;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(repairIngredient));
            }
        }, new Properties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public AbstractWeapon(Tier tier, int meleeAttackDamage) {
        super(tier, new Properties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    public float getDamage(){
        return 1;
    }

    public InteractionHand getWeaponHand(LivingEntity holder) {
        return holder.getMainHandItem().getItem() == this || holder.getOffhandItem().getItem() != this ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }
    public void doProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter){

    }
    public void onPojectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter){

    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
