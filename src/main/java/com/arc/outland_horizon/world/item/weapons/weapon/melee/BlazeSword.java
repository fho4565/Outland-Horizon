package com.arc.outland_horizon.world.item.weapons.weapon.melee;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlazeSword extends AbstractMeleeWeapon {

    public BlazeSword() {
        super(1400, 13,-3.2f, 12, Items.ACACIA_PLANKS,new Properties().rarity(Rarity.EPIC));
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
