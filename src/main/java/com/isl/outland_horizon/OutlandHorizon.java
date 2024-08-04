package com.isl.outland_horizon;

import com.isl.outland_horizon.block.BlockRegistry;
import com.isl.outland_horizon.client.config.Configs;
import com.isl.outland_horizon.level.register.ItemRegistry;
import com.isl.outland_horizon.registry.Effects;
import com.isl.outland_horizon.utils.MaterialPack;
import com.isl.outland_horizon.utils.Utils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {

    public OutlandHorizon() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);
        MaterialPack.create(MaterialPack.MaterialType.GEM,"blue",1);
        MaterialPack.create(MaterialPack.MaterialType.CUSTOM,"type_b",2);
        BlockRegistry.init();
        ItemRegistry.init();
        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        Effects.REGISTRY.register(bus);
    }
}
