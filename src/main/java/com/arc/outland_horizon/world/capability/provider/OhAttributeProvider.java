package com.arc.outland_horizon.world.capability.provider;

import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OhAttributeProvider implements ICapabilityProvider,
        NonNullSupplier<OhAttribute>, ICapabilitySerializable<CompoundTag> {

    public static final Capability<OhAttribute> OH_ATTRIBUTE = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static double mana, manaRecover, maxMana, rage, maxRage, rageRecover, shieldValue, sp, maxSp, spRecover;
    public static int madTime, madDamageBonus;
    private OhAttribute ohAttribute;
    public final LazyOptional<OhAttribute> lazyOptional = LazyOptional.of(() -> this.ohAttribute);

    public OhAttributeProvider() {
        this.ohAttribute = new OhAttribute();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == OH_ATTRIBUTE) {
            return lazyOptional.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull OhAttribute get() {
        return this.ohAttribute;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.ohAttribute.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.ohAttribute.deserializeNBT(nbt);
    }
}
