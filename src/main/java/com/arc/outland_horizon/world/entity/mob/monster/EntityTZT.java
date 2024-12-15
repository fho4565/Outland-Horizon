package com.arc.outland_horizon.world.entity.mob.monster;

import com.arc.outland_horizon.registry.OHEntities;
import com.arc.outland_horizon.world.entity.DamageResistance;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityTZT extends Monster implements DamageResistance {
    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setCreateWorldFog(true).setDarkenScreen(true);

    public EntityTZT(PlayMessages.SpawnEntity packet, Level world) {
        this(OHEntities.TZT.get(), world);
    }

    public EntityTZT(EntityType<EntityTZT> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 6, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5);
        builder = builder.add(Attributes.FLYING_SPEED, 0.5);
        builder = builder.add(Attributes.MAX_HEALTH, 2500);
        builder = builder.add(Attributes.ARMOR, 10);
        builder = builder.add(Attributes.ARMOR_TOUGHNESS, 10);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 24);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        return builder;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void remove(@Nonnull RemovalReason pReason) {
        bossEvent.removeAllPlayers();
        super.remove(pReason);
    }

    @Override
    public void die(@Nonnull DamageSource pDamageSource) {
        bossEvent.removeAllPlayers();
        super.die(pDamageSource);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        Objects.requireNonNull(level().getServer()).getPlayerList().getPlayers().forEach(serverPlayer -> {
            if (this.position().distanceTo(serverPlayer.position()) <= 100) {
                bossEvent.addPlayer(serverPlayer);
            } else {
                bossEvent.removePlayer(serverPlayer);
            }
        });
        super.customServerAiStep();
    }

    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.8));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));

    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public double magicDamageResistance() {
        return 40.0;
    }
}
