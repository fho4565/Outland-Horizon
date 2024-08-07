package com.isl.outland_horizon.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Utils {
    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static ResourceLocation createResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
