package com.arc.outland_horizon.world.item.weapons.weapon.magic.book;

import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.AbstractMagicWeapon;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShieldBook extends AbstractMagicWeapon {
    public ShieldBook() {
        super(100, 10, Items.IRON_INGOT);
    }

    @Override
    public int getCoolDown() {
        return 600;
    }

    @Override
    public int getManaCost() {
        return 25;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.BOMB.get();
    }

    @Override
    public BasePlayerProjectile getProjectile(LivingEntity shooter, AbstractWeapon weapon, int maxAge, int velocity) {
        return null;
    }

    @Override
    public float getBulletMaxAge() {
        return 0;
    }
    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), getSoundEvent(), SoundSource.PLAYERS);
        WorldUtils.getEntitiesByRadio(pLevel, serverPlayer.position(), 8)
                .forEach(entity -> {
                            if (entity instanceof Player player) {
                                CapabilityUtils.addShieldValue(player, 12);
                            }
                        }
                );
    }

    @Override
    public float getBulletVelocity() {
        return 0;
    }

    @Override
    public float getBulletInaccuracy() {
        return 0;
    }
}
