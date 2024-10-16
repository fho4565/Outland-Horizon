package com.arc.outland_horizon.registry.mod_effect;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Utils.MOD_ID);
    public static final RegistryObject<MobEffect> BLEED = register("bleed", Bleed::new);
    public static final RegistryObject<MobEffect> MAD = register("mad", Mad::new);
    public static final RegistryObject<MobEffect> RAGE = register("rage", Rage::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

    public static RegistryObject<MobEffect> register(String id, Supplier<MobEffect> effect) {
        return EFFECTS.register(id, effect);
    }
}
