package com.arc.outland_horizon.world.item.utility;

import com.arc.outland_horizon.ModRarities;
import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DamoclesSword extends Item {
    public DamoclesSword() {
        super(new Properties().stacksTo(1).rarity(ModRarities.LEGENDARY).fireResistant());
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        if (entity instanceof Player player) {
            if (Utils.chanceToTrigger(0.1)) {
                EntityUtils.hurt(player, EntityUtils.getDamageType(player, OutlandHorizon.createModResourceLocation("damocles_sword")), Float.MAX_VALUE);
            }
        }
        super.inventoryTick(itemStack, level, entity, pSlotId, pIsSelected);
    }
}
