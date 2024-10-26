package com.arc.outland_horizon.world.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EmptyProjectileRenderer<T extends Entity> extends EntityRenderer<T> {
    protected EmptyProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        RandomSource random = entity.level().random;
        for (int i = 0; i < 4; i++) {
            entity.level().addParticle(ParticleTypes.ENCHANTED_HIT,
                    entity.getX()+(random.nextFloat()-0.5f)*0.1f,
                    entity.getY()+(random.nextFloat()-0.5f)*0.1f,
                    entity.getZ()+(random.nextFloat()-0.5f)*0.1f,
                    0, 0, 0);
        }
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return new ResourceLocation("","");
    }

}
