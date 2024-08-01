package com.isl.outland_horizon;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configs {
    public static final ForgeConfigSpec.ConfigValue<String> string;

    public static final ForgeConfigSpec COMMON_CONFIG;
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Common");
        string = COMMON_BUILDER.define("string","");
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
