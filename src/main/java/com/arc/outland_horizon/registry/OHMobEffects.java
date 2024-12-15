package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.mob_effects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OHMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OutlandHorizon.MOD_ID);
    public static final RegistryObject<MobEffect> BLEED = register("bleed", Bleed::new);
    public static final RegistryObject<MobEffect> MAD = register("mad", Mad::new);
    public static final RegistryObject<MobEffect> RAGE = register("rage", Rage::new);
    public static final RegistryObject<MobEffect> IRON = register("iron", Iron::new);
    public static final RegistryObject<MobEffect> OH_NIGHT_VISION = register("night_vision_oh", () -> new OHMobEffect(MobEffectCategory.BENEFICIAL, 12779366));
    public static final RegistryObject<MobEffect> NIGHTMARE_COMES = register("nightmare_comes", Nightmare.Comes::new);
    public static final RegistryObject<MobEffect> NIGHTMARE_POSSESSED = register("nightmare_possessed", Nightmare.Possessed::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

    public static RegistryObject<MobEffect> register(String id, Supplier<MobEffect> effect) {
        return EFFECTS.register(id, effect);
    }
}
