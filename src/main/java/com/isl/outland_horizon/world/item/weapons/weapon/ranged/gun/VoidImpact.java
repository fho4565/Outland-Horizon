package com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.isl.outland_horizon.utils.WorldUtils;
import com.isl.outland_horizon.world.entity.projectile.bullet.Bullet;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.AbstractRangedWeapon;
import com.isl.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class VoidImpact extends AbstractRangedWeapon {
    public VoidImpact(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public float getDamage() {
        return 7;
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(pLevel, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEventRegister.MACHINE_GUN.get(), SoundSource.PLAYERS,0.5f,1);
        Bullet bullet = new Bullet(serverPlayer, this, 1, 18);
        bullet.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        bullet.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, 18, 2);
        pLevel.addFreshEntity(bullet);
    }
}
