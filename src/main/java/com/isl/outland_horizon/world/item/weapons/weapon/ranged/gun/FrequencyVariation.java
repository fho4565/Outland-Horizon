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

public class FrequencyVariation extends AbstractRangedWeapon {
    public FrequencyVariation(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability, 1, enchantAbility, repairIngredient);
    }

    @Override
    public int getCoolDown() {
        return 60;
    }

    @Override
    public float getDamage() {
        return 35;
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, InteractionHand pUsedHand) {
        WorldUtils.playSound(pLevel, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEventRegister.SNIPE_GUN.get(), SoundSource.PLAYERS);
        Bullet bullet = new Bullet(serverPlayer, this, 10, 8);
        bullet.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
        bullet.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, 25, 0);
        pLevel.addFreshEntity(bullet);
    }
}
