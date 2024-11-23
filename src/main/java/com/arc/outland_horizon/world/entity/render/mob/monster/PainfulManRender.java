package com.arc.outland_horizon.world.entity.render.mob.monster;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.entity.mob.monster.PainfulMan;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PainfulManRender extends GeoEntityRenderer<PainfulMan> {
    public PainfulManRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(OutlandHorizon.createModResourceLocation("monster/painful_man")));
    }
}

