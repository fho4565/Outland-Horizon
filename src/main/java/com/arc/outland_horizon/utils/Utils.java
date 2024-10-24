package com.arc.outland_horizon.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public static Boolean isDevelopEnvironment(){
        return !FMLEnvironment.production;
    }

    public static String itemResourceLocation(Item item) {
        ArrayList<String> list = Arrays.stream(item.getDescriptionId().split("\\.")).collect(Collectors.toCollection(ArrayList::new));
        list.remove(0);
        return list.get(0) + ":" + list.get(1);
    }
    public static String itemName(Item item) {
        ArrayList<String> list = Arrays.stream(item.getDescriptionId().split("\\.")).collect(Collectors.toCollection(ArrayList::new));
        return list.get(2);
    }
}
