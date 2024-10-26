package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.world.item.ICooldown;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMedal extends Item implements ICooldown {
    public AbstractMedal() {
        super(new Properties());
    }

    public abstract void whenActive(ItemStack stack, Level level, ServerPlayer serverPlayer, int slotId);

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (0 <= slotId && slotId <= 8) {
                whenActive(itemStack, level, serverPlayer, slotId);
            }
        }
        super.inventoryTick(itemStack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return barColor(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return barWidth(stack);
    }
}
