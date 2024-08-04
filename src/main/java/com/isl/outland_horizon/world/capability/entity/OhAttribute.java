package com.isl.outland_horizon.world.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class OhAttribute implements INBTSerializable<CompoundTag> {

    public double mana;
    public double manaRecover;
    public double maxMana;
    public double maxManaRecover;

    public OhAttribute(Entity entity) {
        if (!(entity instanceof Player)) return;

        this.mana = 0.0D;
        this.manaRecover = 0.1D;
        this.maxMana = 100.0D;
        this.maxManaRecover = 100.0D;
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

    public double getMaxManaRecover() {
        return this.maxManaRecover;
    }

    public void setMaxManaRecover(double maxManaRecover) {
        this.maxManaRecover = maxManaRecover;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("mana", this.mana);
        nbt.putDouble("manaRecover", this.manaRecover);
        nbt.putDouble("maxMana", this.maxMana);
        nbt.putDouble("maxManaRecover", this.maxManaRecover);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.mana = nbt.getDouble("mana");
        this.manaRecover = nbt.getDouble("manaRecover");
        this.maxMana = nbt.getDouble("maxMana");
        this.maxManaRecover = nbt.getDouble("maxManaRecover");
    }
}
