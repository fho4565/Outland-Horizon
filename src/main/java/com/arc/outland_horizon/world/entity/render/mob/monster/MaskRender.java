package com.arc.outland_horizon.world.entity.render.mob.monster;

import com.arc.outland_horizon.world.entity.mob.monster.Mask;
import com.arc.outland_horizon.world.entity.model.monster.MaskModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MaskRender extends GeoEntityRenderer<Mask> {
    public MaskRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MaskModel());
    }
}
