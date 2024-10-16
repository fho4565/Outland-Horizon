package com.arc.outland_horizon.world.item.consumables.potions;

import com.arc.outland_horizon.registry.mod_effect.EffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RagePotion extends ConsumablePotion {
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        PotionUtils.addPotionTooltip(getMobEffectInstance(), pTooltipComponents, 1.0f);
    }

    @Override
    public ArrayList<MobEffectInstance> getMobEffectInstance() {
        ArrayList<MobEffectInstance> list = super.getMobEffectInstance();
        list.add(new MobEffectInstance(EffectRegistry.RAGE.get(), 1200));
        return list;
    }

    public RagePotion() {
        super(new Properties().stacksTo(1));
    }
}
