package com.isl.outland_horizon.world.entity.render.mob.monster;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.entity.mob.monster.PainfulMan;
import com.isl.outland_horizon.world.entity.mob.monster.Yee;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class PainfulManRender extends HumanoidMobRenderer<PainfulMan, HumanoidModel<PainfulMan>> {
    public PainfulManRender(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this,
                new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager())
        );
    }

    @Override
    public ResourceLocation getTextureLocation(PainfulMan entity) {
        return new ResourceLocation(Utils.createResourceLocation("textures/entity/monster/painful_man.png").toString());
    }
}

