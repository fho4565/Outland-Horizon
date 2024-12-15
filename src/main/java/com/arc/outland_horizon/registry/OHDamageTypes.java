package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class OHDamageTypes {
    public static final ResourceKey<DamageType> MACHINE_GUN = ResourceKey.create(Registries.DAMAGE_TYPE, OutlandHorizon.createModResourceLocation("machine_gun"));
    public static final ResourceKey<DamageType> DAMOCLES_SWORD = ResourceKey.create(Registries.DAMAGE_TYPE, OutlandHorizon.createModResourceLocation("damocles_sword"));
}
