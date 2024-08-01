package com.isl.outland_horizon;

import com.isl.outland_horizon.generators.RecipeGenerator;
import com.isl.outland_horizon.quicktools.MaterialPackage;
import com.isl.outland_horizon.register.Blocks;
import com.isl.outland_horizon.register.CreativeModeTabs;
import com.isl.outland_horizon.register.Items;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<DataProvider>) RecipeGenerator::new
        );
    }
}
