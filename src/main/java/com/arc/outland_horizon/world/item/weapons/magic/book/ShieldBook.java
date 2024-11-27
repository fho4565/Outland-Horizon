package com.arc.outland_horizon.world.item.weapons.magic.book;

import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.item.IManaCostItem;
import com.arc.outland_horizon.world.item.UsableItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ShieldBook extends UsableItem implements ICooldownItem, IManaCostItem {
    public ShieldBook() {
        super(100, 10, Ingredient.EMPTY, new Properties());
    }

    @Override
    public int getManaCost() {
        return 25;
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.BOMB.get();
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getUseSoundEvent(), SoundSource.PLAYERS);
        WorldUtils.getEntitiesByRadio(pLevel, serverPlayer.position(), 8)
                .forEach(entity -> {
                            if (entity instanceof Player player) {
                                CapabilityUtils.Shield.addShieldValue(player, 12);
                            }
                        }
                );
    }

    @Override
    public int cooldownTime() {
        return Utils.secondsToTicks(30);
    }
}
