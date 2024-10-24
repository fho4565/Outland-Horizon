package com.arc.outland_horizon.world.item.weapons.weapon.magic.wand;

import com.arc.outland_horizon.world.entity.projectile.BasePlayerProjectile;
import com.arc.outland_horizon.world.entity.projectile.magic.FireWandShot;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import com.arc.outland_horizon.world.item.weapons.weapon.magic.AbstractMagicWeapon;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;

public class FireWand extends AbstractMagicWeapon{
    public FireWand() {
        super(300, 10, Items.DIAMOND);
    }
    @Override
    public float getDamage() {
        return 6;
    }
    @Override
    public int getCoolDown() {
        return 20;
    }
    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.STAFF_SHOOT.get();
    }

    @Override
    public BasePlayerProjectile getProjectile(LivingEntity shooter, AbstractWeapon weapon, int maxAge, int velocity) {
        return new FireWandShot(shooter, weapon, maxAge, velocity);
    }

    @Override
    public float getBulletMaxAge() {
        return 60;
    }

    @Override
    public float getBulletVelocity() {
        return 3.0f;
    }

    @Override
    public float getBulletInaccuracy() {
        return 0.0f;
    }
}
