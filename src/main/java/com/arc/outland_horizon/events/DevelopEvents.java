package com.arc.outland_horizon.events;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DevelopEvents {
    public static boolean isDebug = false;
}
