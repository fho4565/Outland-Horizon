package com.arc.outland_horizon.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record Skill(Component name, Component description, int requiredSp, int duration, int cooldown) {
    public void onSkillStart(Player player, ItemStack itemStack, Skill skill) {
    }

    public void onSkillTick(Player player, ItemStack itemStack, Skill skill) {
    }

    public void onSkillEnd(Player player, ItemStack itemStack, Skill skill) {
    }
}
