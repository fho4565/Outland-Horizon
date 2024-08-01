package com.isl.oh;

import com.isl.oh.block.BlockRegistry;
import com.isl.oh.item.weapons.ItemRegistry;
import com.isl.oh.utils.Utils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public static final Logger LOGGER = LogManager.getLogger();

    public OutlandHorizon() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);

        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
    }
}
