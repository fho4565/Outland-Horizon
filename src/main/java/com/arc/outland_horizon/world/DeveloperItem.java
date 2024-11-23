package com.arc.outland_horizon.world;

import com.arc.outland_horizon.utils.ChatUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface DeveloperItem {
    String developerName();

    default Component developerNameTooltip() {
        return ChatUtils.translatable("text.outland_horizon.gui.item.developer", developerName()).withStyle(ChatFormatting.GOLD);
    }
}
