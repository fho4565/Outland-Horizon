package com.isl.outland_horizon.world.item.weapons.weapon.magic;

import com.isl.outland_horizon.utils.ManaUtils;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractMagicWeapon extends AbstractWeapon {
    public AbstractMagicWeapon(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if(pPlayer instanceof ServerPlayer player && pUsedHand.equals(InteractionHand.MAIN_HAND)){
            if(ManaUtils.removeMana(player,getManaCost())){
                player.getCooldowns().addCooldown(this, getCoolDown());
                successfullyUsed(pLevel, player, pUsedHand);
            }else{
                unsuccessfullyUsed(pLevel, player, pUsedHand);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public abstract int getCoolDown();
    public abstract int getManaCost();
    public abstract void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand);
    public void unsuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand){
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("魔力消耗："+ getManaCost()).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
