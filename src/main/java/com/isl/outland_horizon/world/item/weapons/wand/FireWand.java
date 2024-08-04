package com.isl.outland_horizon.world.item.weapons.wand;

import com.isl.outland_horizon.client.renderer.FireWandRenderer;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class FireWand extends AbstractWeapon implements GeoItem {
    private static final RawAnimation ACTIVATE_ANIM = RawAnimation.begin().thenPlay("fire_wand.loop");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FireWand() {
        super(300, 0, 10, Items.DIAMOND);
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
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand interactionHand) {
        Vec3 lookVec = extendVector(player.getLookAngle(), 10);
        Vec3 posVec = player.position();
        SmallFireball smallFireball = new SmallFireball(level, posVec.x, posVec.y + 1, posVec.z, lookVec.x, lookVec.y, lookVec.z);
        smallFireball.setOwner(player);
        level.addFreshEntity(smallFireball);
        player.getCooldowns().addCooldown(this, 20);
        return super.use(level, player, interactionHand);
    }

    public static Vec3 extendVector(Vec3 originalVector, double scaleFactor) {
        double newX = originalVector.x * scaleFactor;
        double newY = originalVector.y * scaleFactor;
        double newZ = originalVector.z * scaleFactor;
        return new Vec3(newX, newY, newZ);
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
