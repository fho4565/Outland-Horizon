package com.isl.oh.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
}
