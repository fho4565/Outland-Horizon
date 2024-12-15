package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.world.entity.mob.monster.EntityTZT;
import com.arc.outland_horizon.world.entity.mob.monster.Mask;
import com.arc.outland_horizon.world.entity.mob.monster.PainfulMan;
import com.arc.outland_horizon.world.entity.mob.monster.Yee;
import com.arc.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.arc.outland_horizon.world.entity.projectile.magic.FireWandShot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

public class OHEntities {
    public static final RegistryObject<EntityType<FireWandShot>> FIREWAND_SHOT = EntityRegistry.Projectiles.registerProjectile("firewandshot", FireWandShot::new);
    public static final RegistryObject<EntityType<Bullet>> IRON_BULLET = EntityRegistry.Projectiles.registerProjectile("iron_bullet", Bullet::new);
    public static final RegistryObject<EntityType<Yee>> YEE = EntityRegistry.register("yee",
            EntityType.Builder.<Yee>of(Yee::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(Yee::new)
                    .sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<PainfulMan>> PAINFUL_MAN = EntityRegistry.register("painful_man",
            EntityType.Builder.<PainfulMan>of(PainfulMan::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(PainfulMan::new)
                    .sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<EntityTZT>> TZT = EntityRegistry.register("entity_tzt",
            EntityType.Builder.<EntityTZT>of(EntityTZT::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(EntityTZT::new)
                    .sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<Mask>> MASK = EntityRegistry.register("mask",
            EntityType.Builder.<Mask>of(Mask::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .setCustomClientFactory(Mask::new)
                    .sized(0.6f, 1.8f));
}
