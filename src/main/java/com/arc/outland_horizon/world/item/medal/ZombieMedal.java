package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ZombieMedal extends AbstractMedal {
    @Override
    public void whenActive(ItemStack stack, Level level, ServerPlayer serverPlayer, int slotId) {
        if (serverPlayer.getHealth() >= serverPlayer.getMaxHealth()) {
            return;
        }
        if (!isCooldown(stack)) {
            serverPlayer.heal(heal());
            startCooldown(stack);
        }
    }

    public abstract int heal();

    public static class Copper extends ZombieMedal {
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
