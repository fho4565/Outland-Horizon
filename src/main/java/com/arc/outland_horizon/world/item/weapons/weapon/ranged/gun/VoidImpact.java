package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class VoidImpact extends Gun {
    public VoidImpact(int maxDurability, int enchantAbility, Item repairIngredient) {
        super(maxDurability,  enchantAbility, repairIngredient);
    }
    @Override
    public int getCoolDown() {
        return 1;
    }

    @Override
    public int getBulletMaxAge() {
        return 5;
    }

    @Override
    public int getBulletVelocity() {
        return 12;
    }

    @Override
    public int getBulletInaccuracy() {
        return 1;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.MACHINE_GUN.get();
    }

    @Override
    public float getDamage() {
        return 7;
    }



    @Override
    public ResourceKey<DamageType> getDamageType(LivingEntity holder) {
        return EntityUtils.getMachineGun(holder,Utils.createModResourceLocation("machine_gun"));
    }
}
