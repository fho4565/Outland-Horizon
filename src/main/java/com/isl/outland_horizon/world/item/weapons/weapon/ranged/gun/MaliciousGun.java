package com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.isl.outland_horizon.utils.WorldUtils;
import com.isl.outland_horizon.world.entity.projectile.bullet.IronBullet;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.AbstractRangedWeapon;
import com.isl.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class MaliciousGun extends AbstractRangedWeapon {
    public MaliciousGun(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }

    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public float getDamage() {
        return 3;
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(pLevel, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEventRegister.GUN.get(), SoundSource.PLAYERS);
        IronBullet ironBullet = new IronBullet(serverPlayer, this, 180, 8);
        ironBullet.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        pLevel.addParticle(ParticleTypes.FLAME,
                serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z,
                0.1, 0.1, 0.1);
        ironBullet.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, 20, 0);
        pLevel.addFreshEntity(ironBullet);
    }
}
