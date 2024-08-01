package com.isl.oh;

import com.isl.oh.registry.BlockRegistry;
import com.isl.oh.registry.ItemRegistry;
import com.isl.oh.utils.Utils;
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

        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCKS.register(bus);


    }
}
