package com.arc.outland_horizon;

import com.arc.outland_horizon.config.Configs;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.block.*;
import com.arc.outland_horizon.registry.item.*;
import com.arc.outland_horizon.registry.mod_effect.MobEffectRegistry;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.entity.EntityRegistry;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public static IEventBus bus;
    public OutlandHorizon() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);
        initClasses();
        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        OHBlocks.Fluid.FluidRegistry.FLUIDS.register(bus);
        OHBlocks.Fluid.FluidTypeRegistry.FLUID_TYPES.register(bus);
        MobEffectRegistry.register(bus);
        EntityRegistry.EntityRenders.init();
        EntityRegistry.register(bus);
        SoundEventRegister.init();
        SoundEventRegister.SOUND.register(bus);
    }

    private static void initClasses() {
        Medal.init();
    }

}
