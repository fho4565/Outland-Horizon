package com.isl.outland_horizon.item.weapons.magic.wand;

import com.isl.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireWandModel extends GeoModel<FireWand> {
    private final ResourceLocation model = new ResourceLocation(Utils.MOD_ID, "geo/item/fire_wand.geo.json");
    private final ResourceLocation texture = new ResourceLocation(Utils.MOD_ID, "textures/item/fire_wand.png");
    private final ResourceLocation animations = new ResourceLocation(Utils.MOD_ID, "animations/item/fire_wand.animation.json");

    @Override
    public ResourceLocation getModelResource(FireWand animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(FireWand animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(FireWand animatable) {
        return this.animations;
    }
}
