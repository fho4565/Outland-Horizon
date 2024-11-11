package com.arc.outland_horizon.world.item.weapons.weapon.magic.book;

import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.AbstractMagicWeapon;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class DespairBook extends AbstractMagicWeapon {
    public DespairBook() {
        super(60, 15, OHItems.Material.NIGHTMARE_ENERGY.get());
    }

    @Override
    public int getCoolDown() {
        return 600;
    }

    @Override
    public int getManaCost() {
        return 50;
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
        serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, Utils.secondsToTicks(25), 2));
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
