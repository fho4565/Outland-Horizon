package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangZH_CN extends LanguageProvider {
    public LangZH_CN(PackOutput output, String locale) {
        super(output, Utils.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(OHItems.Tool.BLOOD_BUCKET, "血桶");
        addItem(OHItems.Tool.BLOOD_STONE_AXE, "血石斧");
    }
}
