package com.arc.outland_horizon.world.item.medal;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ZombieMedal extends AbstractMedal{
    public static class Copper extends ZombieMedal{
        @Override
        public int heal() {
            return 2;
        }

        @Override
        public int cooldown() {
            return 200;
        }
    }
    public static class Silver extends ZombieMedal{
        @Override
        public int heal() {
            return 4;
        }

        @Override
        public int cooldown() {
            return 160;
        }
    }
    public static class Gold extends ZombieMedal{
        @Override
        public int heal() {
            return 5;
        }

        @Override
        public int cooldown() {
            return 100;
        }
    }
    @Override
    public void whenActive(ItemStack stack, Level level, ServerPlayer serverPlayer, int slotId) {
        if(serverPlayer.getHealth() == serverPlayer.getMaxHealth()){
            return;
        }
        if(!serverPlayer.getCooldowns().isOnCooldown(this)){
            serverPlayer.heal(heal());
            serverPlayer.getCooldowns().addCooldown(this, cooldown());
        }
    }
    public abstract int heal();
    public abstract int cooldown();
}
