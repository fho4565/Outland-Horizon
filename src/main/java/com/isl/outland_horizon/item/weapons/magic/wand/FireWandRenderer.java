package com.isl.outland_horizon.item.weapons.magic.wand;


import com.isl.outland_horizon.utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FireWandRenderer extends GeoItemRenderer<FireWand> {
    public FireWandRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Utils.MOD_ID, "fire_wand")));
    }
}
