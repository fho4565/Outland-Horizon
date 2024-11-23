package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public interface ICooldownItem {
    String COOLDOWN_TAG = "cooldown";

    int cooldownTime();

    default boolean isCooldown(ItemStack stack) {
        return getCooldown(stack) > 0;
    }

    default int getCooldown(ItemStack stack) {
        if (stack.getOrCreateTag().contains(Utils.MOD_ID)) {
            return stack.getOrCreateTag().getCompound(Utils.MOD_ID).getInt(COOLDOWN_TAG);
        }
        return 0;
    }

    default void startCooldown(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(Utils.MOD_ID)) {
            stack.getOrCreateTag().put(Utils.MOD_ID, new CompoundTag());
        }
        stack.getOrCreateTag().getCompound(Utils.MOD_ID).putInt(COOLDOWN_TAG, cooldownTime());
    }

    default void stopCooldown(ItemStack stack) {
        if (stack.getOrCreateTag().contains(Utils.MOD_ID)) {
            stack.getOrCreateTag().getCompound(Utils.MOD_ID).putInt(COOLDOWN_TAG, 0);
        }
    }

    default void tickCooldown(ItemStack stack) {
        if (isCooldown(stack)) {
            CompoundTag tag = stack.getOrCreateTag().getCompound(Utils.MOD_ID);
            tag.putInt(COOLDOWN_TAG, tag.getInt(COOLDOWN_TAG) - 1);
        }
    }

    default int barColor(ItemStack stack) {
        return Utils.getColorForBar(1 - (float) getCooldown(stack) / cooldownTime());
    }

    default int barWidth(ItemStack stack) {
        return Utils.getScaledBarWidth((float) getCooldown(stack) / cooldownTime());
    }

    default Component cooldownTooltip() {
        return ChatUtils.translatable("text.outland_horizon.gui.weapon.reload_cooldown", cooldownTime()).withStyle(ChatFormatting.DARK_GREEN);
    }
}
