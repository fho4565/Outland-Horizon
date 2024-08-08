package com.isl.outland_horizon.client.renderer;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.item.weapons.weapon.magic.wand.FireWand;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class FireWandRenderer extends GeoItemRenderer<FireWand> {
    public FireWandRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Utils.MOD_ID, "fire_wand")));
    }
}
