package com.arc.outland_horizon.world.item.medal;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractMedal extends Item {
    public AbstractMedal() {
        super(new Properties());
    }
    public abstract void whenActive(ItemStack stack, Level level, ServerPlayer serverPlayer, int slotId);
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pEntity instanceof ServerPlayer serverPlayer) {
            if(0<=pSlotId&&pSlotId<=8){
                whenActive(pStack, pLevel, serverPlayer, pSlotId);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
