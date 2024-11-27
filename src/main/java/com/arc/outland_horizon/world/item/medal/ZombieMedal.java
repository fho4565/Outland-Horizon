package com.arc.outland_horizon.world.item.medal;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class ZombieMedal extends AbstractMedal {
    @Override
    public void onCooldownStart(Player player, ItemStack itemStack) {
        player.heal(heal());
    }

    @Override
    public boolean autoCooldown(Player player, ItemStack itemStack) {
        return player.getHealth() < player.getMaxHealth();
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
