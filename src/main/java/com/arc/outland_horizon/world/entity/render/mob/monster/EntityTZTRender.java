package com.arc.outland_horizon.world.entity.render.mob.monster;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.entity.mob.monster.EntityTZT;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EntityTZTRender extends HumanoidMobRenderer<EntityTZT, HumanoidModel<EntityTZT>> {
    public EntityTZTRender(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager())
        );
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EntityTZT entity) {
        return new ResourceLocation(Utils.createModResourceLocation("textures/entity/monster/boss/entity_tzt.png").toString());
    }
}
