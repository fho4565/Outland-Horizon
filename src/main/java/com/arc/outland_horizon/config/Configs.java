package com.arc.outland_horizon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configs {
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<String> keycd1;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Common");
        keycd1 = COMMON_BUILDER.define("string","default");
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
