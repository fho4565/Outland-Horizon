package com.isl.outland_horizon.level.capa;

import com.isl.outland_horizon.level.capa.data.OhAttributes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OhCapaHandler implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<OhAttributes> PLAYER_ATTRIBUTE= CapabilityManager.get(new CapabilityToken<>() {});//创建一个空能力

    private OhAttributes H2ATTRIBUTE=null;

    private final LazyOptional<OhAttributes> optional=LazyOptional.of(this::createAttribute);//将能力创建出来

    @NotNull
    private OhAttributes createAttribute() {
        if (this.H2ATTRIBUTE==null){
            this.H2ATTRIBUTE=new OhAttributes();
        }
        return this.H2ATTRIBUTE;

    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability==PLAYER_ATTRIBUTE){
            return optional.cast();
        }
        return  LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {//序列化
        CompoundTag Attribute=new CompoundTag();
        createAttribute().saveNbtData(Attribute);
        return Attribute;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createAttribute().loadNbtData(compoundTag);
    }//反序列化
}
