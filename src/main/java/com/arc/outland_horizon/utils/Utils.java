package com.arc.outland_horizon.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static final Logger LOGGER = LogManager.getLogger();

    public static Boolean isDevelopEnvironment() {
        return !FMLEnvironment.production;
    }

    public static String getDescriptionIdName(Item item) {
        String[] split = item.getDescriptionId().split("\\.");
        return split[split.length - 1];
    }

    public static String getDescriptionIdName(Block block) {
        String[] split = block.getDescriptionId().split("\\.");
        return split[split.length - 1];
    }

    public static int getColorForBar(float scale) {
        return Mth.hsvToRgb(Math.max(0.0F, scale) / 3.0F, 1.0F, 1.0F);
    }

    public static int getScaledBarWidth(float barLength, float scale) {
        return Math.round(barLength - barLength * scale);
    }

    public static int secondsToTicks(float seconds) {
        return Math.round(seconds * 20);
    }

    /**
     * @param chance 触发事件的概率，以百分比表示（例如，50表示50%的概率）
     * @return boolean 表示事件是否被触发 true表示触发，false表示未触发
     * @throws IllegalArgumentException 参数不正确时抛出异常
     */
    public static boolean chanceToTrigger(double chance) {
        chance /= 100.0;
        if (chance < 0.0 || chance > 1.0) {
            throw new IllegalArgumentException("参数不正确，chance必须大于0小于100！");
        }
        return ThreadLocalRandom.current().nextDouble() < chance;
    }
}
