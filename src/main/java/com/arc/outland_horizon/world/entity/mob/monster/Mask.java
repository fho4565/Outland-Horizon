package com.arc.outland_horizon.world.entity.mob.monster;

import com.arc.outland_horizon.registry.OHEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Mask extends Monster implements GeoEntity {
    protected static final RawAnimation ROTATE_ANIM = RawAnimation.begin().thenLoop("rotate");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Mask(EntityType<? extends Mask> type, Level level) {
        super(type, level);
    }

    public Mask(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(OHEntities.MASK.get(), level);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void baseTick() {
        Level level = this.level();
        BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
        if (level.getBlockState(pos).getBlock() == Blocks.TORCH) {
            level.destroyBlock(pos, false);
        }
        super.baseTick();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return super.getAttackReachSqr(entity);
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.goalSelector.addGoal(2, new BreakDoorGoal(this, difficulty -> true));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.TORCH, this, 1, 8));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.GLOWSTONE, this, 1, 8));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.CAMPFIRE, this, 1, 8));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.REDSTONE_LAMP, this, 1, 8));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.LANTERN, this, 1, 8));
        this.goalSelector.addGoal(2, new RemoveBlockGoal(Blocks.JACK_O_LANTERN, this, 1, 8));
        this.goalSelector.addGoal(2, new FollowMobGoal(this, 1, 6.0f, 12.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public static void init() {
        SpawnPlacements.register(OHEntities.MASK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "rotate", 5, this::rotateAnimController));
    }

    protected <E extends Mask> PlayState rotateAnimController(final AnimationState<E> event) {
        if (!event.isMoving()) {
            return event.setAndContinue(ROTATE_ANIM);
        }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
