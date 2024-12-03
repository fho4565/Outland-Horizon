package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class ZombieMedal extends AbstractMedal {

    public ZombieMedal(Properties properties) {
        super(properties);
    }

    @Nonnull
    private static Properties properties() {
        return new Properties().stacksTo(1);
    }

    @Override
    public void onCooldownStart(Player player, ItemStack itemStack) {
        player.heal(heal());
    }

    public abstract int heal();

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        boolean active = 0 <= slotId && slotId <= 8;
        setRenderCooldownBarWhenEnds(itemStack, true);
        if (entity instanceof ServerPlayer player) {
            boolean on = player.getHealth() < player.getMaxHealth();
            setShouldTick(itemStack, active);
            setAutoCooldown(itemStack, on && active);
        }
        super.inventoryTick(itemStack, level, entity, slotId, isSelected);
    }


    public static class Copper extends ZombieMedal {
        public Copper() {
            super(properties());
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

    public static class Silver extends ZombieMedal {
        public Silver() {
            super(properties().rarity(Rarity.UNCOMMON));
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

    public static class Gold extends ZombieMedal {
        public Gold() {
            super(properties().rarity(Rarity.EPIC));
        }

        @Override
        public int heal() {
            return 5;
        }


        @Override
        public int cooldownTime() {
            return Utils.secondsToTicks(10);
        }
    }
}
