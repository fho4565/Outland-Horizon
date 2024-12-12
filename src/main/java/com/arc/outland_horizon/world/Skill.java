package com.arc.outland_horizon.world;

import com.arc.outland_horizon.utils.CapabilityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Skill {
    private String id;
    private Component name;
    private Component description;
    private int requiredSp;
    private int duration;
    private int cooldown;

    public Skill(String id, Component name, Component description, int requiredSp, int duration, int cooldown) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredSp = requiredSp;
        this.duration = duration;
        this.cooldown = cooldown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean autoReduceDuration() {
        return true;
    }

    public int cooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public Component description() {
        return description;
    }

    public void setDescription(Component description) {
        this.description = description;
    }

    public int duration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Component name() {
        return name;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public int requiredSp() {
        return requiredSp;
    }

    public void setRequiredSp(int requiredSp) {
        this.requiredSp = requiredSp;
    }

    public void onSkillStart(Player player, ItemStack itemStack) {
    }

    public void onSkillTick(Player player, ItemStack itemStack) {
    }

    public void onSkillEnd(Player player, ItemStack itemStack) {
    }

    public boolean canTrigger(Player player) {
        return CapabilityUtils.Sp.isSpSufficient(player, requiredSp);
    }
}
