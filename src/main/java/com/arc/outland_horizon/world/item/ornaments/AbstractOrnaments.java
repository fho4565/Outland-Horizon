package com.arc.outland_horizon.world.item.ornaments;

import com.arc.outland_horizon.world.item.ICooldownItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractOrnaments extends Item implements ICooldownItem, ICurioItem {
    public AbstractOrnaments(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltipComponents, pIsAdvanced);
        tooltipComponents.add(cooldownTooltip());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        whenWear(slotContext, stack);
        ICurioItem.super.curioTick(slotContext, stack);
    }

    protected void whenWear(SlotContext slotContext, ItemStack stack) {

    }
}
