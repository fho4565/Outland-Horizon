package com.isl.outland_horizon.world.item.weapons.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.isl.outland_horizon.client.renderer.FireWandRenderer;
import com.isl.outland_horizon.utils.functions.FiveParaConsumer;
import com.isl.outland_horizon.utils.functions.FourParaConsumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractWeapon extends TieredItem {
    protected final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public MutableComponent hoverText = Component.literal("");
    protected TriConsumer<ThrowableProjectile, Vec3, LivingEntity> onProjectileHitBlock = (projectile, vec3, entity) -> {};
    protected TriConsumer<ThrowableProjectile, Entity, LivingEntity> onProjectileHitEntity = (projectile, vec3, entity) -> {};
    protected Supplier<Float> getDamage = ()-> 1.0F;
    protected FiveParaConsumer<Entity,ItemStack,Integer,Level,Boolean> whenInInventory = (entity, itemStack, slotId, level,isHolding) -> {};
    protected Consumer<UseOnContext> useOn = (useonContext) -> {};
    protected TriConsumer<Level,Player,InteractionHand> use = (level, player, hand) -> {};
    protected TriConsumer<ItemStack,LivingEntity,LivingEntity> whenAttacked = (itemStack,sourceEntity,targetEntity) -> {};

    protected AbstractWeapon(int maxDurability, int meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        super(new Tier() {
            public int getUses() {
                return maxDurability;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return meleeAttackDamage;
            }

            public int getLevel() {
                return 0;
            }

            public int getEnchantmentValue() {
                return enchantAbility;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(repairIngredient));
            }
        }, new Properties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    protected AbstractWeapon(Tier tier, int meleeAttackDamage) {
        super(tier, new Properties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    public AbstractWeapon whenAttacked(TriConsumer<ItemStack, LivingEntity, LivingEntity> whenAttacked) {
        this.whenAttacked = whenAttacked;
        return this;
    }
    public AbstractWeapon use(TriConsumer<Level,Player,InteractionHand> use) {
        this.use = use;
        return this;
    }
    public AbstractWeapon useOn(Consumer<UseOnContext> useOn) {
        this.useOn = useOn;
        return this;
    }
    public AbstractWeapon whenInInventory(FiveParaConsumer<Entity,ItemStack,Integer,Level,Boolean> whenInInventory) {
        this.whenInInventory = whenInInventory;
        return this;
    }
    public Supplier<Item> build(){
        return ()->this;
    }
    @Override
    public void inventoryTick(@NotNull ItemStack p_41404_, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        whenInInventory.accept(p_41406_, p_41404_, p_41407_, p_41405_,p_41408_);
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext p_41427_) {
        useOn.accept(p_41427_);
        return super.useOn(p_41427_);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player p_41433_, InteractionHand p_41434_) {
        use.accept(p_41432_, p_41433_, p_41434_);
        return super.use(p_41432_, p_41433_, p_41434_);
    }
    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        whenAttacked.accept(itemStack, attacker, target);
        return super.hurtEnemy(itemStack, target, attacker);
    }

    public AbstractWeapon onProjectileHitBlock(TriConsumer<ThrowableProjectile, Vec3, LivingEntity> onProjectileHitBlock){
        this.onProjectileHitBlock = onProjectileHitBlock;
        return this;
    }
    public AbstractWeapon onProjectileHitEntity(TriConsumer<ThrowableProjectile, Entity, LivingEntity> onProjectileHitEntity){
        this.onProjectileHitEntity = onProjectileHitEntity;
        return this;
    }
    public AbstractWeapon getDamage(Supplier<Float> damage){
        this.getDamage = damage;
        return this;
    }
    public AbstractWeapon hoverText(MutableComponent hoverText){
        this.hoverText = hoverText;
        return this;
    }
    public float getDamage(){
        return getDamage.get();
    };
    public InteractionHand getWeaponHand(LivingEntity holder) {
        return holder.getMainHandItem().getItem() == this || holder.getOffhandItem().getItem() != this ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }
    public void doProjectileHitBlock(ThrowableProjectile projectile, Vec3 location, LivingEntity shooter){
        onProjectileHitBlock.accept(projectile, location, shooter);
    }
    public void doProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter){
        onProjectileHitEntity.accept(projectile, target, shooter);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(hoverText);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
