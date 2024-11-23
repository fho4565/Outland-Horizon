package com.arc.outland_horizon.world.item.weapons.magic.book;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.item.IManaCostItem;
import com.arc.outland_horizon.world.item.UsableItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DespairBook extends UsableItem implements IManaCostItem, ICooldownItem {
    public DespairBook() {
        super(75, 15, Ingredient.EMPTY, new Properties());
    }

    @Override
    public int getManaCost() {
        return 50;
    }

    @Override
    public @NotNull SoundEvent getUseSoundEvent() {
        return SoundEventRegister.BOMB.get();
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getUseSoundEvent(), SoundSource.PLAYERS);
        serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, Utils.secondsToTicks(25), 2));
    }

    @Override
    public int cooldownTime() {
        return 50;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag pIsAdvanced) {
        components.add(Component.empty());
        components.add(manaCostTooltip());
        components.add(cooldownTooltip());
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
    }
}
