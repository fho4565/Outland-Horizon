package com.arc.outland_horizon.world.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.awt.*;

public class Nightmare{
    public static class Possessed extends MobEffect {
        public Possessed() {
            super(MobEffectCategory.HARMFUL, Color.RED.getRGB());
        }
    }
    public static class Comes extends MobEffect {
        public Comes() {
            super(MobEffectCategory.HARMFUL, Color.RED.getRGB());
        }
    }
}
