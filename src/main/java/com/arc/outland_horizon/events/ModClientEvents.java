package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.client.key.KeyRegistry;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.arc.outland_horizon.registry.OHBlocks.Fluids.OHFluids.*;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        KeyRegistry.register(event);
    }

    @SubscribeEvent
    public static void addItemToVanillaTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.OP_BLOCKS)) {
            event.accept(OHBlocks.Functional.TEXTURES_TEST_BLOCK);
            event.accept(OHItems.Tool.DEBUGGER);
        }
    }

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BLOOD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BLOOD_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ArterialBLOOD.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ArterialBLOOD_FLOWING.get(), RenderType.translucent());
        event.enqueueWork(() -> {
            ItemProperties.register(OHItems.Weapon.Tank.Shield.Buckler.BLOOD_STONE_BUCKLER.get(), OutlandHorizon.createModResourceLocation("is_blocking"), (pStack, pLevel, pEntity, pSeed) -> {
                if (pEntity != null) {
                    return pEntity.getUseItem().is(OHItems.Weapon.Tank.Shield.Buckler.BLOOD_STONE_BUCKLER.get()) ? 1.0f : 0.0f;
                }
                return 0.0f;
            });
        });
    }
}
