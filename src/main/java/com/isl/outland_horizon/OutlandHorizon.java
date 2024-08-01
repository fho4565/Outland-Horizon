package com.isl.outland_horizon;

import com.isl.outland_horizon.client.config.Configs;
import com.isl.outland_horizon.client.generators.RecipeGenerator;
import com.isl.outland_horizon.network.PacketHandler;
import com.isl.outland_horizon.quicktools.MaterialPackage;
import com.isl.outland_horizon.level.register.Blocks;
import com.isl.outland_horizon.level.register.CreativeModeTabs;
import com.isl.outland_horizon.level.register.Items;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public OutlandHorizon() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
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
    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
        MixinBootstrap.init();//启用mixin
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("notch");//启用mixin




    }
}
