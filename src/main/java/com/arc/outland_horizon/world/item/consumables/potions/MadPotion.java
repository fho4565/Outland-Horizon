package com.arc.outland_horizon.world.item.consumables.potions;

import com.arc.outland_horizon.registry.MobEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MadPotion extends ConsumablePotion {
    public MadPotion() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, @Nonnull List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        PotionUtils.addPotionTooltip(getMobEffectInstance(), pTooltipComponents, 1.0f);
    }

    @Override
    public ArrayList<MobEffectInstance> getMobEffectInstance() {
        ArrayList<MobEffectInstance> list = super.getMobEffectInstance();
        list.add(new MobEffectInstance(MobEffectRegistry.MAD.get(), 1200));
        return list;
    }
}
