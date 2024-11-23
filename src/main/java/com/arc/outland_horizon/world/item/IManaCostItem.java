package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.utils.ChatUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface IManaCostItem {
    int getManaCost();

    default Component manaCostTooltip() {
        return ChatUtils.translatable("text.outland_horizon.gui.weapon.magic_cost", getManaCost()).withStyle(ChatFormatting.DARK_GREEN);
    }
}
