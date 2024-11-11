package com.arc.outland_horizon;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

public class ModRarities {
    public static final Rarity LEGENDARY = Rarity.create("legendary", ChatFormatting.GOLD);
    public static final Rarity VOID = Rarity.create("void", style -> Style.EMPTY.withColor(ChatFormatting.DARK_RED));

}
