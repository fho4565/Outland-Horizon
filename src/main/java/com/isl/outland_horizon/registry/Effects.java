package com.isl.outland_horizon.registry;

import com.isl.outland_horizon.potion.Bleed;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Effects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Utils.MOD_ID);
    public static final RegistryObject<MobEffect> BLEED = REGISTRY.register("bleed", Bleed::new);

}
