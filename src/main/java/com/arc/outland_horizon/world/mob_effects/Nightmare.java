package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.List;

public class Nightmare {
    public static class Possessed extends MobEffect {
        public Possessed() {
            super(MobEffectCategory.HARMFUL, Color.RED.getRGB());
        }

        @Override
        public java.util.List<ItemStack> getCurativeItems() {
            return List.of();
        }
    }

    public static class Comes extends MobEffect {
        public Comes() {
            super(MobEffectCategory.HARMFUL, Color.RED.getRGB());
        }

        @Override
        public List<ItemStack> getCurativeItems() {
            return List.of();
        }
    }
}
