package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.client.key.KeyRegistry;
import com.arc.outland_horizon.client.particle.NightmareRainSplash;
import com.arc.outland_horizon.registry.ItemRegistry;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.registry.ParticleRegistry;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.item.weapons.tank.buckler.Buckler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

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
        event.enqueueWork(() -> {
            ItemProperties.registerGeneric(OutlandHorizon.createModResourceLocation("is_blocking"), (pStack, pLevel, pEntity, pSeed) -> {
                if (pEntity != null) {
                    return (pEntity.getUseItem().getItem() instanceof Buckler) ? 1.0f : 0.0f;
                }
                return 0.0f;
            });
        });
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.NIGHTMARE_RAIN_SPLASH.get(), NightmareRainSplash::provider);
    }

    @SubscribeEvent
    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        ItemRegistry.ITEM_LIST.forEach(itemRegistryObject -> {
            Item item = itemRegistryObject.get();
            if (item instanceof ICooldownItem cooldownItem) {
                IItemDecorator decorator = (guiGraphics, font, stack, x, y) -> {
                    if (cooldownItem.shouldRenderCooldownBar(stack)) {
                        guiGraphics.pose().pushPose();
                        if (cooldownItem.isCooldown(stack) || cooldownItem.renderCooldownBarWhenEnds(stack)) {
                            int barWidth = cooldownItem.cooldownBarWidth(stack);
                            int barColor = cooldownItem.cooldownBarColor(stack);
                            guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 1, y + 15, -16777216);
                            guiGraphics.fill(RenderType.guiOverlay(), x, y + 15, x + 1, y + 15 - barWidth, barColor | -16777216);
                        }
                        guiGraphics.pose().popPose();
                    }
                    return true;
                };
                event.register(item, decorator);
            }
        });

    }

    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(OutlandHorizon.createModResourceLocation("matrix"), new DimensionSpecialEffects.OverworldEffects() {
            @Override
            public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
                return true;
            }

            @Override
            public boolean constantAmbientLight() {
                return false;
            }

            @Override
            public boolean forceBrightLightmap() {
                return true;
            }

            @Nullable
            @Override
            public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
                return null;
            }

            @Override
            public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
                return true;
            }
        });
    }


}
