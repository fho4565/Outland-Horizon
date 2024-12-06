package com.arc.outland_horizon;

import com.arc.outland_horizon.config.Configs;
import com.arc.outland_horizon.registry.BlockRegistry;
import com.arc.outland_horizon.registry.ItemRegistry;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHMobEffects;
import com.arc.outland_horizon.world.entity.EntityRegistry;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OutlandHorizon.MOD_ID)
public class OutlandHorizon {

    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static IEventBus bus;

    public OutlandHorizon() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);
        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        OHBlocks.Fluid.FluidRegistry.FLUIDS.register(bus);
        OHBlocks.Fluid.FluidTypeRegistry.FLUID_TYPES.register(bus);
        OHMobEffects.register(bus);
        EntityRegistry.EntityRenders.init();
        EntityRegistry.register(bus);
        SoundEventRegister.init();
        SoundEventRegister.SOUND.register(bus);
    }

    public static ResourceLocation createModResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
