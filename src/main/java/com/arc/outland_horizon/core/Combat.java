package com.arc.outland_horizon.core;

public class Combat {
    public static float calculateDamageWithArmor(float damage, float armor, float armorToughness) {
        float line = getArmorBreakPoint(armor, armorToughness);
        float remainDamage = Math.max(0, damage - line * 0.05f);
        float reduceScale = line / (line + remainDamage * remainDamage * remainDamage);
        return damage > line ? Math.max(1, damage * (1 - reduceScale)) : Math.max(0, Math.min(1, damage * 0.02f));
    }

    public static float getArmorBreakPoint(float armor, float armorToughness) {
        return armor * (20 + armorToughness) / 20;
    }
}
