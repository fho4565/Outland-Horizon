package com.isl.outland_horizon.world.entity;

import com.isl.outland_horizon.OutlandHorizon;
import com.isl.outland_horizon.world.entity.mob.monster.EntityTZT;
import com.isl.outland_horizon.world.entity.mob.monster.PainfulMan;
import com.isl.outland_horizon.world.entity.mob.monster.Yee;
import com.isl.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.isl.outland_horizon.world.entity.projectile.magic.FireWandShot;
import com.isl.outland_horizon.world.entity.render.mob.monster.EntityTZTRender;
import com.isl.outland_horizon.world.entity.render.mob.monster.PainfulManRender;
import com.isl.outland_horizon.world.entity.render.mob.monster.YeeRender;
import com.isl.outland_horizon.world.entity.render.projectile.bullet.BulletRenderer;
import com.isl.outland_horizon.world.entity.render.projectile.magic.FireWandShotRender;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.SharedConstants;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "outland_horizon");
    public static final RegistryObject<EntityType<FireWandShot>> FIREWAND_SHOT = Projectiles.registerProjectile("firewandshot", FireWandShot::new);
    public static final RegistryObject<EntityType<Bullet>> IRON_BULLET = Projectiles.registerProjectile("iron_bullet", Bullet::new);

    public static final RegistryObject<EntityType<Yee>> YEE = register("yee",
            EntityType.Builder.<Yee>of(Yee::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(Yee::new)
                    .sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<PainfulMan>> PAINFUL_MAN = register("painful_man",
            EntityType.Builder.<PainfulMan>of(PainfulMan::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PainfulMan::new)
                    .sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<EntityTZT>> MOBA = register("entity_tzt",
            EntityType.Builder.<EntityTZT>of(EntityTZT::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(EntityTZT::new)
                    .sized(0.6f, 1.8f));
    public static void register(IEventBus bus){
        ENTITIES.register(bus);
    }
    public static class Projectiles {
        private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(String registryName, EntityType.EntityFactory<T> factory) {
            return registerProjectile(registryName, factory, 0.25f, 0.25f);
        }

        private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(String registryName, EntityType.EntityFactory<T> factory, float width, float height) {
            return registerProjectile(registryName, factory, width, height, 3);
        }

        private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(String registryName, EntityType.EntityFactory<T> factory, float width, float height, int updateInterval) {
            return registerProjectile(registryName, factory, width, height, updateInterval, EntityType.Builder::noSave);
        }

        private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(String registryName, EntityType.EntityFactory<T> factory, float width, float height, int updateInterval, Consumer<EntityType.Builder<T>> builderMod) {
            EntityType.Builder<T> typeBuilder = EntityType.Builder.of(factory, MobCategory.MISC)
                    .sized(width, height).clientTrackingRange(8)
                    .setTrackingRange(120).setUpdateInterval(updateInterval);

            builderMod.accept(typeBuilder);

            return ENTITIES.register(registryName, () -> {
                boolean dataFixers = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
                SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
                EntityType<T> entityType = typeBuilder.build(registryName);
                SharedConstants.CHECK_DATA_FIXER_SCHEMA = dataFixers;

                return entityType;
            });
        }
    }
    public static class EntityRenders {
        private static List<EntityRendererPackage> RENDERER_PACKAGES = new ObjectArrayList<>();
        public static final EntityRendererPackage<?> POISON_SHOT = new EntityRendererPackage<>(EntityRegistry.FIREWAND_SHOT)
                .provider(FireWandShotRender::new);
        public static final EntityRendererPackage<?> IRON_BULLET = new EntityRendererPackage<>(EntityRegistry.IRON_BULLET)
                .provider(BulletRenderer::new);

        public static void init() {
            OutlandHorizon.bus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterRenderers.class, EntityRegistry.EntityRenders::registerEntityRenderers);
            OutlandHorizon.bus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterLayerDefinitions.class, EntityRegistry.EntityRenders::registerLayerDefinitions);
            OutlandHorizon.bus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.AddLayers.class, EntityRegistry.EntityRenders::onRenderLayerRegistration);
        }

        private static void registerEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            for (EntityRendererPackage<Entity> rendererPackage : RENDERER_PACKAGES) {
                event.registerEntityRenderer(rendererPackage.entityType.get(), rendererPackage.build());
            }
            event.registerEntityRenderer(YEE.get(), YeeRender::new);
            event.registerEntityRenderer(PAINFUL_MAN.get(), PainfulManRender::new);
            event.registerEntityRenderer(MOBA.get(), EntityTZTRender::new);
            RENDERER_PACKAGES = null;

        }

        private static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions ev) {
            for (EntityRendererPackage<?> rendererPackage : RENDERER_PACKAGES) {
                rendererPackage.registerModelLayer(ev);
            }
        }

        private static void onRenderLayerRegistration(final EntityRenderersEvent.AddLayers ev) {
            for (String skin : ev.getSkins()) {
                LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer = ev.getSkin(skin);

            }
        }
        public static class EntityRendererPackage<T extends Entity> {
            protected final RegistryObject<EntityType<T>> entityType;
            protected final HashMap<String, Pair<ModelLayerLocation, Supplier<LayerDefinition>>> layerDefinitions = new HashMap<>();
            protected EntityRendererProvider<T> rendererProvider = null;
            protected float shadowSize = -1;
            private EntityRendererPackage(RegistryObject<EntityType<T>> entityType) {
                this.entityType = entityType;
                RENDERER_PACKAGES.add(this);
            }
            private EntityRendererPackage<T> provider(EntityRendererProvider provider) {
                this.rendererProvider = provider;
                return this;
            }
            private void registerModelLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
                for (Pair<ModelLayerLocation, Supplier<LayerDefinition>> layer : this.layerDefinitions.values()) {
                    event.registerLayerDefinition(layer.getFirst(), layer.getSecond());
                }
            }
            protected EntityRendererProvider<T> build() {
                if (this.rendererProvider == null)
                    throw new IllegalStateException("No registered renderer provider for entity: " + this.entityType.getId());

                if (this.shadowSize == -1)
                    this.shadowSize = this.entityType.get().getWidth() / 3f;

                return this.rendererProvider;
            }
        }
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return ENTITIES.register(registryname, () -> entityTypeBuilder.build(registryname));
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(Yee::init);
        event.enqueueWork(PainfulMan::init);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(YEE.get(), Yee.createAttributes().build());
        event.put(PAINFUL_MAN.get(), PainfulMan.createAttributes().build());
        event.put(MOBA.get(), EntityTZT.createAttributes().build());
    }

}
