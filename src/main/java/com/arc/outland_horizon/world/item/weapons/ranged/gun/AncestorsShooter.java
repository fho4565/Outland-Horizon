package com.arc.outland_horizon.world.item.weapons.ranged.gun;

import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.Skill;
import com.arc.outland_horizon.world.item.ISkillItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AncestorsShooter extends Gun implements ISkillItem {
    final Skill skill1 = new Skill("zzfsq", Component.literal("a"),
            Component.literal("b"),
            0, 6, Utils.secondsToTicks(60)
    ) {
        @Override
        public boolean autoReduceDuration() {
            return false;
        }

        @Override
        public void onSkillEnd(Player player, ItemStack itemStack) {
            super.onSkillEnd(player, itemStack);
        }

        @Override
        public void onSkillStart(Player player, ItemStack itemStack) {
            super.onSkillStart(player, itemStack);
        }

        @Override
        public void onSkillTick(Player player, ItemStack itemStack) {
            super.onSkillTick(player, itemStack);
        }
    };
    boolean active = false;

    public AncestorsShooter() {
        super(225, 15, Ingredient.EMPTY, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public SoundEvent getUseSoundEvent() {
        return SoundEventRegister.GUN.get();
    }

    @Override
    public float getDamage() {
        return 10 + (active ? 30 : 0);
    }

    @Override
    public void successfullyUsed(Level pLevel, ServerPlayer serverPlayer, ItemStack itemStack) {
        if (isCurrentSkillActive(itemStack)) {
            reduceDuration(serverPlayer, itemStack);
        }
        super.successfullyUsed(pLevel, serverPlayer, itemStack);
    }

    @Override
    public void onProjectileHitEntity(Projectile projectile, Entity target, LivingEntity shooter) {
        if (active) {
            WorldUtils.getEntitiesByRadio(target.level(), target.position(), 8, entity -> (!entity.equals(shooter) && entity instanceof Monster || entity instanceof Enemy)).forEach(entity -> {
                if (entity instanceof LivingEntity enemy) {
                    EntityUtils.hurt(shooter, enemy, DamageTypes.MOB_ATTACK,
                            getDamage() / 2
                    );
                }
            });
        }
        super.onProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        active = isCurrentSkillActive(pStack);
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public int cooldownTime() {
        return 20 + (active ? 80 : 0);
    }

    @Override
    public int getBulletMaxAge() {
        return 6;
    }

    @Override
    public float getBulletVelocity() {
        return 5 + (active ? 13 : 0);
    }

    @Override
    public Skill skill1() {
        return skill1;
    }
}
