package com.isl.outland_horizon.world.entity.render.projectile.bullet;

import com.isl.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.isl.outland_horizon.world.entity.render.projectile.EmptyProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BulletRenderer extends EmptyProjectileRenderer<Bullet> {
    public BulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }


}
