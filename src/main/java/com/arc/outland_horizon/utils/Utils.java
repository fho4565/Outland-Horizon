package com.arc.outland_horizon.utils;

import net.minecraft.resources.ResourceLocation;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Utils {
    public static final String MOD_ID = "outland_horizon";
    public static final String MOD_NAME = "Outland Horizon";
    public static ResourceLocation createResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
    public static UUID generateUUIDFromText(String text) {
        // 将文本转换为字节数组
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        // 从字节数组生成UUID
        return UUID.nameUUIDFromBytes(textBytes);
    }
}
