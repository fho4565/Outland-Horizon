package com.isl.outland_horizon.world.entity.projectile;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

public class FireWandShotRender extends ParticleProjectileRenderer<FireWandShot> {
    public FireWandShotRender(final EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    protected void addParticles(FireWandShot entity, float partialTicks) {
        RandomSource random = entity.level().random;
        for (int i = 0; i < 4; i++) {
            entity.level().addParticle(ParticleTypes.FLAME,
                    entity.getX()+(random.nextFloat()-0.5f)*0.1f,
                    entity.getY()+(random.nextFloat()-0.5f)*0.1f,
                    entity.getZ()+(random.nextFloat()-0.5f)*0.1f,
                    0, 0, 0);
        }
    }
}
