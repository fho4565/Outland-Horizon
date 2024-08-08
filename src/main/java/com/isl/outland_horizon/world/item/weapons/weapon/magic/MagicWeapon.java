package com.isl.outland_horizon.world.item.weapons.weapon.magic;

import com.isl.outland_horizon.utils.ManaUtils;
import com.isl.outland_horizon.utils.functions.FourParaConsumer;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicWeapon extends AbstractWeapon {
    protected int coolDown = 20,manaCost = 10;
    protected FourParaConsumer<Level,ServerPlayer,AbstractWeapon,InteractionHand> successfullyUsed = (level, serverPlayer,itemStack, hand)->{};
    protected FourParaConsumer<Level,ServerPlayer,AbstractWeapon,InteractionHand> unsuccessfullyUsed = (level, serverPlayer,itemStack, hand)->{};
    protected MagicWeapon(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
        this.use = (level, player, hand)->{
            if(player instanceof ServerPlayer serverPlayer){
                if(ManaUtils.removeMana(player, manaCost)){
                    doSuccessfullyUsed(level, serverPlayer, hand);
                    player.getCooldowns().addCooldown(this, coolDown);
                }else{
                    doUnsuccessfullyUsed(level, serverPlayer, hand);
                }
            }
        };
    }
    public static MagicWeapon of(int maxDurability, int enchantAbility, Item repairIngredient) {
        return new MagicWeapon(maxDurability, enchantAbility, repairIngredient) ;
    }
    public MagicWeapon coolDown(int coolDown){
        this.coolDown = coolDown;
        return this;
    };
    public MagicWeapon manaCost(int manaCost){
        this.manaCost = manaCost;
        return this;
    }
    public MagicWeapon successfullyUsed(FourParaConsumer<Level,ServerPlayer,AbstractWeapon,InteractionHand> successfullyUsed){
        this.successfullyUsed = successfullyUsed;
        return this;
    }
    public MagicWeapon unsuccessfullyUsed(FourParaConsumer<Level,ServerPlayer,AbstractWeapon,InteractionHand> unsuccessfullyUsed){
        this.unsuccessfullyUsed = unsuccessfullyUsed;
        return this;
    }
    protected void doSuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand){
        successfullyUsed.accept(pLevel, serverPlayer,this, pUsedHand);
    }
    protected void doUnsuccessfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand){
        unsuccessfullyUsed.accept(pLevel, serverPlayer,this, pUsedHand);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("魔力消耗："+ manaCost).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
