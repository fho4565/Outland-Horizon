package com.isl.outland_horizon.world.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class OhAttribute implements INBTSerializable<CompoundTag> {
    public double mana;
    public double manaRecover;
    public double maxMana;
    public double rage, maxRage,rageRecover;

    public OhAttribute(Entity entity) {
        if (!(entity instanceof Player)) return;
        this.mana = 100.0D;
        this.manaRecover = 0.1D;
        this.maxMana = 200.0D;
        this.rage = 0.0D;
        this.maxRage = 1000.0D;
        this.rageRecover = 1.0D;
    }
    public double getMana() {
        return this.mana;
    }
    public void setMana(double mana) {
        this.mana = mana;
    }
    public double getManaRecover() {
        return this.manaRecover;
    }
    public void setManaRecover(double manaRecover) {
        this.manaRecover = manaRecover;
    }
    public double getMaxMana() {
        return this.maxMana;
    }
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }
    public double getRage() {
        return this.rage;
    }

    public void setRage(double rage) {
        this.rage = rage;
    }

    public double getRageRecover() {
        return this.rageRecover;
    }

    public void setRageRecover(double rageRecover) {
        this.rageRecover = rageRecover;
    }

    public double getMaxRage() {
        return this.maxRage;
    }

    public void setMaxRage(double maxRage) {
        this.maxRage = maxRage;
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("mana", this.mana);
        nbt.putDouble("manaRecover", this.manaRecover);
        nbt.putDouble("maxMana", this.maxMana);
        nbt.putDouble("rage", this.rage);
        nbt.putDouble("rageRecover", this.rageRecover);
        nbt.putDouble("maxRage", this.maxRage);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.mana = nbt.getDouble("mana");
        this.manaRecover = nbt.getDouble("manaRecover");
        this.maxMana = nbt.getDouble("maxMana");
        this.rage = nbt.getDouble("rage");
        this.rageRecover = nbt.getDouble("rageRecover");
        this.maxRage = nbt.getDouble("maxRage");
    }
}
