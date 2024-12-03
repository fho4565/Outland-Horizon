package com.arc.outland_horizon.world.item.weapons.magic;

import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.world.item.IManaCostItem;
import com.arc.outland_horizon.world.item.UsableItem;
import com.arc.outland_horizon.world.item.weapons.IRangedWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class MagicWeapon extends UsableItem implements IRangedWeapon, IManaCostItem {
    public MagicWeapon(int uses, int enchantmentValue, Ingredient repairIngredient, Properties pProperties) {
        super(uses, enchantmentValue, repairIngredient, pProperties);
    }

    @Override
    public boolean canUse(Level pLevel, Player pPlayer, ItemStack itemStack) {
        return CapabilityUtils.Mana.isManaSufficient(pPlayer, getManaCost()) && super.canUse(pLevel, pPlayer, itemStack);
    }

    public abstract void whenUse(Level pLevel, Player pPlayer, ItemStack itemStack);

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, ItemStack itemStack) {
        CapabilityUtils.Mana.removeMana(serverPlayer, getManaCost());
        whenUse(pLevel, serverPlayer, itemStack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(manaCostTooltip());
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
