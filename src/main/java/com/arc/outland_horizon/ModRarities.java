package com.arc.outland_horizon;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

import java.awt.*;

public class ModRarities {
    public static final Rarity LEGENDARY = Rarity.create("legendary", ChatFormatting.GOLD);
    public static final Rarity DISASTER = Rarity.create("disaster", style -> Style.EMPTY.withColor(new Color(123, 0, 0).getRGB()));
    public static final Rarity VOID = Rarity.create("void", style -> Style.EMPTY.withColor(ChatFormatting.DARK_RED));

}
