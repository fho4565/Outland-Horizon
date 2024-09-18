package com.isl.outland_horizon.world.item.consumables.potions;

import com.isl.outland_horizon.utils.ManaUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ManaPotion extends ConsumablePotion {
    public ManaPotion() {
        super(new Properties().stacksTo(16));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if(livingEntity instanceof ServerPlayer player){
            ManaUtils.addMana(player,100);
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltips, @NotNull TooltipFlag flag) {
        MutableComponent component = MutableComponent.create(ComponentContents.EMPTY);
        component.append(Component.literal("恢复").withStyle(ChatFormatting.YELLOW));
        component.append(Component.literal("100").withStyle(ChatFormatting.LIGHT_PURPLE));
        component.append(Component.literal("魔力").withStyle(ChatFormatting.YELLOW));
        tooltips.add(component);
        super.appendHoverText(itemStack, level, tooltips, flag);
    }
}
