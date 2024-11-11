package com.arc.outland_horizon.world.item.consumables.potions;

import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.ChatUtils;
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

public abstract class ManaPotion extends ConsumablePotion {
    public ManaPotion() {
        super(new Properties().stacksTo(16));
    }
    public abstract int manaRecover();
    public static class Common extends ManaPotion{
        public Common() {
            super();
        }
        @Override
        public int manaRecover() {
            return 50;
        }
    }
    public static class Intermediate extends ManaPotion{
        public Intermediate() {
            super();
        }
        @Override
        public int manaRecover() {
            return 100;
        }
    }
    public static class Advanced extends ManaPotion{
        public Advanced() {
            super();
        }
        @Override
        public int manaRecover() {
            return 150;
        }
    }
    public static class Super extends ManaPotion{
        public Super() {
            super();
        }
        @Override
        public int manaRecover() {
            return 200;
        }
    }
    public static class Ultimate extends ManaPotion{
        public Ultimate() {
            super();
        }
        @Override
        public int manaRecover() {
            return 300;
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if(livingEntity instanceof ServerPlayer player){
            CapabilityUtils.addMana(player,manaRecover());
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltips, @NotNull TooltipFlag flag) {
        MutableComponent component = MutableComponent.create(ComponentContents.EMPTY);
        component.append(ChatUtils.translatable("text.outland_horizon.gui.consumable.magic_potion",manaRecover()).withStyle(ChatFormatting.DARK_GREEN));
        tooltips.add(component);
        super.appendHoverText(itemStack, level, tooltips, flag);
    }
}
