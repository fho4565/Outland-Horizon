package com.isl.outland_horizon;

import com.isl.outland_horizon.config.Configs;
import com.isl.outland_horizon.utils.MaterialPack;
import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.effect.EffectRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public OutlandHorizon() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);

        MaterialPack.create(MaterialPack.MaterialType.GEM, "blue", 1);
        MaterialPack.create(MaterialPack.MaterialType.CUSTOM, "type_b", 2);

        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        BlockRegistry.FluidRegistry.FLUIDS.register(bus);
        BlockRegistry.FluidTypeRegistry.FLUID_TYPES.register(bus);
        EffectRegistry.register(bus);
    }

}
