package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.world.item.ICooldownItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMedal extends Item implements ICooldownItem {
    boolean shouldTick;

    public AbstractMedal() {
        super(new Properties());
    }


    @Override
    public boolean shouldTick(Player player, ItemStack itemStack) {
        return shouldTick;
    }

    @Override
    public boolean shouldRenderCooldownBar(ItemStack stack) {
        return shouldTick;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer) {
            shouldTick = 0 <= slotId && slotId <= 8;
        }
        super.inventoryTick(itemStack, level, entity, slotId, isSelected);
    }
}
