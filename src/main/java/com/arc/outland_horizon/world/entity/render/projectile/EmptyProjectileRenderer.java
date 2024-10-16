package com.arc.outland_horizon.world.entity.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EmptyProjectileRenderer<T extends Entity> extends EntityRenderer<T> {
    protected EmptyProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return null;
    }

}
