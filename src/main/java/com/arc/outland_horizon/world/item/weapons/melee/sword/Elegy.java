package com.arc.outland_horizon.world.item.weapons.melee.sword;

import com.arc.outland_horizon.ModRarities;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

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
              }, 76, 8,
                new Properties().rarity(ModRarities.DISASTER));
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity sourceLivingEntity) {
            if (sourceLivingEntity.getMainHandItem().getItem() == OHItems.Weapon.Melee.Sword.ELEGY.get()) {
                System.out.println("A");
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
        if (event.getEntity().getMobType() == MobType.UNDEAD) {
            event.setAmount(event.getAmount() * 1.75f);
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
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
