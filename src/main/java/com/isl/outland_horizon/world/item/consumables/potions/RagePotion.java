package com.isl.outland_horizon.world.item.consumables.potions;

import com.isl.outland_horizon.world.mod_effect.EffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class RagePotion extends ConsumablePotion {
    public RagePotion() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(EffectRegistry.RAGE.get(),3600));
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltips, @NotNull TooltipFlag flag) {
        PotionUtils.addPotionTooltip(itemStack, tooltips, 1.0F);
    }
}
