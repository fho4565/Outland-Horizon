package com.arc.outland_horizon.world.item.weapons.weapon;

import com.arc.outland_horizon.utils.EntityUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractWeapon extends TieredItem {
    public static final String WEAPON_MODIFIER_NAME = "Weapon modifier";
    protected Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AbstractWeapon(int maxDurability, float meleeAttackDamage,float attackSpeed, int enchantAbility, Item repairIngredient) {
        this(new Tier() {
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
        }, meleeAttackDamage,attackSpeed);
    }
    public AbstractWeapon(int maxDurability, float meleeAttackDamage,float attackSpeed, int enchantAbility, Item repairIngredient,Properties properties) {
        this(new Tier() {
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
        }, meleeAttackDamage,attackSpeed,properties);
    }
    public AbstractWeapon(int maxDurability, float meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        this(new Tier() {
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
        }, meleeAttackDamage,-2.4f);
        createModifiers(meleeAttackDamage,-2.4f, Map.of());
    }

    public AbstractWeapon(Tier tier,float attackSpeed, float meleeAttackDamage) {
        super(tier, new Properties());
        createModifiers(meleeAttackDamage, attackSpeed, Map.of());
    }
    public AbstractWeapon(Tier tier,float meleeAttackDamage,float attackSpeed, Properties properties) {
        super(tier, properties);
        createModifiers(meleeAttackDamage, attackSpeed, Map.of());
    }
    public float getDamage(){
        return 1;
    }

    public InteractionHand getWeaponHand(LivingEntity holder) {
        return holder.getMainHandItem().getItem() == this || holder.getOffhandItem().getItem() != this ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }
    public void onProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter){

    }
    public void onProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter){
        EntityUtils.hurt(shooter, target, DamageTypes.MAGIC, (float) (getDamage()+shooter.getAttributeValue(Attributes.ATTACK_DAMAGE)));

    }
    public void createModifiers(float meleeAttackDamage, float attackSpeed, Map<Attribute,AttributeModifier> additionalModifiers){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, WEAPON_MODIFIER_NAME, meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, WEAPON_MODIFIER_NAME, attackSpeed, AttributeModifier.Operation.ADDITION));
        additionalModifiers.forEach(builder::put);
        this.defaultModifiers = builder.build();
    }
}
