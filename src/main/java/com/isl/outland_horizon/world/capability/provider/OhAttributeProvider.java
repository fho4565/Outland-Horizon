package com.isl.outland_horizon.world.capability.provider;

import com.isl.outland_horizon.world.capability.ModCapabilities;
import com.isl.outland_horizon.world.capability.entity.OhAttribute;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OhAttributeProvider implements ICapabilityProvider, NonNullSupplier<OhAttribute>, ICapabilitySerializable<CompoundTag> {

    private final OhAttribute capability;

    public OhAttributeProvider(Entity entity) {
        this.capability = new OhAttribute(entity);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapabilities.OH_ATTRIBUTE ? LazyOptional.of(this).cast() : LazyOptional.empty();
    }

    @Override
    public @NotNull OhAttribute get() {
        return this.capability;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.capability.deserializeNBT(nbt);
    }
}
