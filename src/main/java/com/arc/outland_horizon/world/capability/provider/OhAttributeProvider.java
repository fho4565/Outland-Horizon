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

    private OhAttribute ohAttribute;
    public static final Capability<OhAttribute> OH_ATTRIBUTE = CapabilityManager.get(new CapabilityToken<>() {});
    public final LazyOptional<OhAttribute> lazyOptional = LazyOptional.of(() -> this.ohAttribute);
    public static double mana,manaRecover, maxMana,rage, maxRage,rageRecover;
    public OhAttributeProvider() {
        this.ohAttribute = new OhAttribute();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == OH_ATTRIBUTE){
            return lazyOptional.cast();
        }else{
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

    public OhAttributeProvider(OhAttribute ohAttribute) {
        this.ohAttribute = ohAttribute;
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
