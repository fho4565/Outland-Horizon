package com.arc.outland_horizon.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Utils {
    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation createModResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static UUID generateUUIDFromText(String text) {
        return UUID.nameUUIDFromBytes(text.getBytes(StandardCharsets.UTF_8));
    }

    public static Boolean isDevelopEnvironment() {
        return !FMLEnvironment.production;
    }

    public static String itemName(Item item) {
        return item.getDescriptionId().split("\\.")[2];
    }

    public static int getColorForBar(float scale) {
        return Mth.hsvToRgb(Math.max(0.0F, scale) / 3.0F, 1.0F, 1.0F);
    }

    public static int getScaledBarWidth(float scale) {
        return Math.round(13.0F - 13.0F * scale);
    }
    public static int secondsToTicks(float seconds) {
        return Math.round(seconds * 20);
    }
}
