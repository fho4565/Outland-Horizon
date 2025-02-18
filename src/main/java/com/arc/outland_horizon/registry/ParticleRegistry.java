package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, OutlandHorizon.MOD_ID);
    public static final RegistryObject<SimpleParticleType> NIGHTMARE_RAIN_SPLASH = register("nightmare_rain_splash", new SimpleParticleType(false));


    public static RegistryObject<SimpleParticleType> register(String key, SimpleParticleType particleType) {
        return PARTICLE_TYPE.register(key, () -> particleType);
    }
}
