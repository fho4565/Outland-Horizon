package com.arc.outland_horizon.world.item.weapons.melee.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlazeSword extends SwordItem {

    public BlazeSword() {
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
        }, 13, -3.2f, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, LivingEntity target, @NotNull LivingEntity attacker) {
        target.setRemainingFireTicks(100);
        return super.hurtEnemy(itemStack, target, attacker);
    }


    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("点燃敌人").withStyle(ChatFormatting.RED));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
