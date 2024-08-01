package com.isl.oh;

import com.isl.oh.quicktools.MaterialPackage;
import com.isl.oh.register.Blocks;
import com.isl.oh.register.CreativeModeTabs;
import com.isl.oh.register.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public OutlandHorizon() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);
        MaterialPackage.create("type", MaterialPackage.MaterialType.INGOT, 2);
        Blocks.init();
        Items.init();
        Blocks.BLOCK_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Items.ITEM_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        CreativeModeTabs.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(bus);
    }
}
