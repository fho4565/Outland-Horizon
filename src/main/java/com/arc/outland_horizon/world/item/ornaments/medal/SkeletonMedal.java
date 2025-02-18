package com.arc.outland_horizon.world.item.ornaments.medal;

import com.arc.outland_horizon.core.ModDataManager;
import com.arc.outland_horizon.core.ModDifficulties;
import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.ItemUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.item.ornaments.AbstractOrnaments;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class SkeletonMedal extends AbstractOrnaments {
    public SkeletonMedal(Item.Properties properties) {
        super(properties);
    }

    @Nonnull
    private static Item.Properties properties() {
        return new Item.Properties();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        setRenderCooldownBarWhenEnds(stack, true);
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        setAutoCooldown(stack, entity.getHealth() < entity.getMaxHealth());
        super.curioTick(slotContext, stack);
    }

    @Override
    public void onCooldownStart(Player player, ItemStack itemStack) {
        player.heal(heal());
        itemStack.setDamageValue(itemStack.getDamageValue() + 1);
        ItemUtils.damageItemStack(player, itemStack, 1);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltipComponents, pIsAdvanced);
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.ornaments.medal.zombie.heal", heal()).withStyle(ChatFormatting.DARK_GREEN));
    }

    public abstract int heal();

    public static class Copper extends SkeletonMedal {
        public Copper() {
            super(properties().durability(150));
        }

        @Override
        public int heal() {
            return 2;
        }


        @Override
        public int cooldownTime() {
            return Utils.secondsToTicks(20);
        }
    }

    public static class Silver extends SkeletonMedal {
        public Silver() {
            super(properties().rarity(Rarity.UNCOMMON).durability(300));
        }

        @Override
        public int heal() {
            return 4;
        }


        @Override
        public int cooldownTime() {
            return Utils.secondsToTicks(15);
        }
    }

    public static class Gold extends SkeletonMedal {
        public Gold() {
            super(properties().rarity(Rarity.EPIC).durability(500));
        }

        @Override
        public int heal() {
            return 5;
        }


        @Override
        public int cooldownTime() {
            return Utils.secondsToTicks(10);
        }

        @Override
        public void curioTick(SlotContext slotContext, ItemStack itemStack) {
            super.curioTick(slotContext, itemStack);
            setShouldTick(itemStack, ModDataManager.modDifficulties == ModDifficulties.ETERNAL);
        }

        @Override
        public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, tooltipComponents, pIsAdvanced);
            if (ModDataManager.modDifficulties != ModDifficulties.ETERNAL) {
                tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.ornaments.medal.gold.warn").withStyle(ChatFormatting.RED));
            }
        }
    }
}
