package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.world.item.ICooldownItem;
import net.minecraft.world.item.Item;

public abstract class AbstractMedal extends Item implements ICooldownItem {
    public AbstractMedal(Properties properties) {
        super(properties);
    }
}
