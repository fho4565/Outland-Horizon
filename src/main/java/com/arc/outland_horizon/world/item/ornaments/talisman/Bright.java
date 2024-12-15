package com.arc.outland_horizon.world.item.ornaments.talisman;

import com.arc.outland_horizon.registry.OHMobEffects;
import com.arc.outland_horizon.utils.ItemUtils;
import com.arc.outland_horizon.world.item.ornaments.AbstractOrnaments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class Bright extends AbstractOrnaments {
    public Bright() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public int cooldownTime() {
        return 200;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        setRenderCooldownBarWhenEnds(stack, true);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        setAutoCooldown(stack, !entity.hasEffect(OHMobEffects.OH_NIGHT_VISION.get()));
        super.curioTick(slotContext, stack);
    }

    @Override
    public void onCooldownStart(Player player, ItemStack itemStack) {
        player.addEffect(new MobEffectInstance(OHMobEffects.OH_NIGHT_VISION.get(), 220));
        ItemUtils.damageItemStack(player, itemStack, 1);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltipComponents, pIsAdvanced);
    }

}
