package com.arc.outland_horizon.world.entity.model.monster;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.world.entity.mob.monster.Mask;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MaskModel extends GeoModel<Mask> {
    @Override
    public ResourceLocation getModelResource(Mask animatable) {
        return OutlandHorizon.createModResourceLocation("geo/entity/monster/mask.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mask animatable) {
        return OutlandHorizon.createModResourceLocation("textures/entity/monster/mask.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mask animatable) {
        return OutlandHorizon.createModResourceLocation("animations/entity/monster/mask.animation.json");
    }
}
