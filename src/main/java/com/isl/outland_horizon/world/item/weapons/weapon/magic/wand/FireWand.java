package com.isl.outland_horizon.world.item.weapons.weapon.magic.wand;

import com.isl.outland_horizon.client.renderer.FireWandRenderer;
import com.isl.outland_horizon.utils.EntityUtils;
import com.isl.outland_horizon.world.item.weapons.weapon.magic.MagicWeapon;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class FireWand extends MagicWeapon implements GeoItem {
    private static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin().thenPlay("fire_wand.loop");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FireWand() {
        super(300, 10, Items.DIAMOND);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FireWandRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new FireWandRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Activation", 0, state -> PlayState.STOP)
                .triggerableAnim("activate", ACTIVATE_ANIM));
    }

    @Override
    public void doProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        EntityUtils.hurt(shooter, target, DamageTypes.MAGIC, getDamage());
        super.doProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public void doProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter) {
        super.doProjectileHitBlock(projectile, location, shooter);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel instanceof ServerLevel serverLevel) {
            triggerAnim(pEntity, GeoItem.getOrAssignId(pStack, serverLevel), "Activation", "activate");
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
