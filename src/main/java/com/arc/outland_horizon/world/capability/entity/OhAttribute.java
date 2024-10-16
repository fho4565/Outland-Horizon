package com.arc.outland_horizon.world.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class OhAttribute implements INBTSerializable<CompoundTag> {
    public double mana,manaRecover,maxMana;
    public double rage, maxRage,rageRecover;

    public OhAttribute() {
        this.mana = 200.0D;
        this.manaRecover = 1.0d;
        this.maxMana = 200.0D;
        this.rage = 0.0D;
        this.maxRage = 100.0D;
        this.rageRecover = 1.0D;
    }
    public double getMana() {
        return this.mana;
    }
    public void setMana(double mana) {
        this.mana = mana;
    }
    public double getManaRecover() {
        return this.manaRecover/20.0d;
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
        return this.rageRecover/20.0d;
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
