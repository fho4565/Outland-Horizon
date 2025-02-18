package com.arc.outland_horizon.world.item.weapons.melee.sword;

import com.arc.outland_horizon.core.ModRarities;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber
public class Elegy extends SwordItem {
    public Elegy() {
        super(new Tier() {
                  @Override
                  public int getUses() {
                      return 3500;
                  }

                  @Override
                  public float getSpeed() {
                      return 8;
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
                      return 15;
                  }

                  @Override
                  public @NotNull Ingredient getRepairIngredient() {
                      return Ingredient.EMPTY;
                  }
              }, 46, -2.7f,
                new Properties().rarity(ModRarities.DISASTER));
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity sourceLivingEntity) {
            if (sourceLivingEntity.getMainHandItem().getItem() == OHItems.Weapon.Melee.Sword.ELEGY.get()) {
                LivingEntity entity = event.getEntity();
                ThreadLocalRandom random = ThreadLocalRandom.current();
                if (entity.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            35,
                            random.nextDouble(0, 1),
                            random.nextDouble(0, 1),
                            random.nextDouble(0, 1),
                            random.nextDouble(0, 1)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getMainHandItem().getItem() == OHItems.Weapon.Melee.Sword.ELEGY.get()) {
                if (event.getEntity().getMobType() == MobType.UNDEAD) {
                    event.setAmount(event.getAmount() * 1.75f);
                }
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand interactionHand) {
        ItemStack item = player.getItemInHand(interactionHand);
        InteractionHand otherHand = interactionHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherItem = player.getItemInHand(otherHand);
        if (otherItem.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK) && !player.getCooldowns().isOnCooldown(otherItem.getItem())) {
            return InteractionResultHolder.fail(item);
        } else {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(item);
        }
    }

    @Override
    public void onUseTick(@Nonnull Level level, @NotNull LivingEntity livingEntity, @Nonnull ItemStack stack, int pRemainingUseDuration) {
        WorldUtils.getEntitiesByRadio(level, livingEntity.position(), 8, entity -> (!entity.equals(livingEntity) && entity instanceof Monster || entity instanceof Enemy))
                .forEach(entity -> {
                    if (entity instanceof LivingEntity enemy) {
                        Multimap<Attribute, AttributeModifier> attributeModifiers = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);
                        double dmg = 0;
                        for (Attribute key : attributeModifiers.keys()) {
                            if (key == Attributes.ATTACK_DAMAGE) {
                                dmg += attributeModifiers.get(key).stream().mapToDouble(AttributeModifier::getAmount).sum();
                            }
                        }
                        AttributeInstance attribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
                        EntityUtils.hurt(livingEntity, enemy, DamageTypes.MOB_ATTACK,
                                (float) (dmg + (attribute != null ? attribute.getValue() : 0)) * 0.03f
                        );
                    }
                });
        super.onUseTick(level, livingEntity, stack, pRemainingUseDuration);
    }

    @Override
    public void releaseUsing(@Nonnull ItemStack itemStack, @Nonnull Level level, @Nonnull LivingEntity livingEntity, int pTimeCharged) {
        if (pTimeCharged >= Utils.secondsToTicks(5)) {
            WorldUtils.getEntitiesByRadio(level, livingEntity.position(), 3, entity -> (!entity.equals(livingEntity) && entity instanceof Monster || entity instanceof Enemy))
                    .forEach(entity -> {
                        if (entity instanceof LivingEntity target) {
                            Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
                            double dmg = 0;
                            for (Attribute key : attributeModifiers.keys()) {
                                if (key == Attributes.ATTACK_DAMAGE) {
                                    dmg += attributeModifiers.get(key).stream().mapToDouble(AttributeModifier::getAmount).sum();
                                }
                            }
                            AttributeInstance attribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
                            target.addEffect(new MobEffectInstance(MobEffects.WITHER, Utils.secondsToTicks(10)));
                            EntityUtils.hurt(livingEntity, target, DamageTypes.MOB_ATTACK,
                                    (float) (dmg + (attribute != null ? attribute.getValue() : 0)) * Math.min(1.5f, pTimeCharged / 200.0f)
                            );
                            if (livingEntity instanceof Player player) {
                                player.getCooldowns().addCooldown(itemStack.getItem(), pTimeCharged / 200);
                            }
                        }
                    });
        }
        super.releaseUsing(itemStack, level, livingEntity, pTimeCharged);
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return Utils.secondsToTicks(20);
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, @Nonnull Level pLevel, @Nonnull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!itemStack.isEnchanted()) {
            itemStack.enchant(Enchantments.SWEEPING_EDGE, 5);
            itemStack.enchant(Enchantments.SHARPNESS, 5);
            itemStack.enchant(Enchantments.UNBREAKING, 3);
        }
        super.inventoryTick(itemStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return super.getSweepHitBox(stack, player, target).inflate(1.0);
    }


}
