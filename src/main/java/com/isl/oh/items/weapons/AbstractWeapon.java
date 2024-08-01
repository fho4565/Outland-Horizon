package com.isl.oh.items.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWeapon extends TieredItem {
    final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final float attackDamage;

    /**
     * 武器基类
     * @param maxDurability 武器的最大耐久度，也决定了武器的使用次数。
     * @param meleeAttackDamage 武器的近战攻击伤害加成，影响武器的基础伤害。
     * @param enchantAbility 武器的附魔能力值，决定了武器可以附魔的等级。
     * @param repairIngredient 用于修复武器的物品，决定了武器的修复材料。
     */
    public AbstractWeapon(int maxDurability,int meleeAttackDamage,int enchantAbility,Item repairIngredient) {
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
        this.attackDamage = meleeAttackDamage;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    /**
     * 武器基类
     *
     * @param tier 武器的等级。例如“木”，“钻石”，“下界合金”
     * @param meleeAttackDamage 武器的近战攻击伤害，直接影响武器的攻击力。
     */
    public AbstractWeapon(Tier tier,int meleeAttackDamage) {
        super(tier, new Properties());
        this.attackDamage = meleeAttackDamage;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", meleeAttackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    /**
     * 当手持物品攻击时调用
     *
     * @param itemStack 用来攻击的物品
     * @param sourceEntity 攻击的来源实体
     * @param targetEntity 被攻击的目标实体
     */
    public void whenAttacked(ItemStack itemStack, LivingEntity sourceEntity, LivingEntity targetEntity){}
    /**
     * 当手持物品左键空气时调用
     *
     * @param player 触发事件的玩家
     * @param itemStack 物品
     */
    public void whenLeftClickedAir(Player player,ItemStack itemStack){}
    /**
     * 当手持物品左键方块时调用
     *
     * @param player 触发事件的玩家
     * @param itemStack 物品
     */
    public void whenLeftClickedBlock(Player player,ItemStack itemStack){}
    /**
     * 当手持物品右键空气时调用
     *
     * @param player 触发事件的玩家
     * @param itemStack 物品
     */
    public void whenRightClickedAir(Player player,ItemStack itemStack){}
    /**
     * 当手持物品右键方块时调用
     *
     * @param player 触发事件的玩家
     * @param itemStack 物品
     */
    public void whenRightClickedBlock(Player player,ItemStack itemStack){}
    /**
     * 当手持物品时调用
     *
     * @param entity 手持物品的实体
     * @param itemStack 物品
     */
    public void whenHolding(Entity entity,ItemStack itemStack){}
    /**
     * 当背包中拥有此物品时调用
     *
     * @param entity 拥有物品的实体
     * @param itemStack 物品
     * @param slotId 背包栏位ID
     */
    public void whenInInventory(Entity entity,ItemStack itemStack,int slotId){}
    public float getDamage() {
        return this.attackDamage;
    }
    @Override
    public void inventoryTick(@NotNull ItemStack p_41404_, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        if(p_41408_){
            whenHolding(p_41406_,p_41404_);
        }else{
            whenInInventory(p_41406_,p_41404_,p_41407_);
        }
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext p_41427_) {
        super.useOn(p_41427_);
        switch (p_41427_.getHand()) {
            case MAIN_HAND -> whenLeftClickedBlock(p_41427_.getPlayer(),p_41427_.getItemInHand());
            case OFF_HAND -> whenRightClickedBlock(p_41427_.getPlayer(),p_41427_.getItemInHand());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player p_41433_, InteractionHand p_41434_) {
        switch (p_41434_) {
            case MAIN_HAND -> whenLeftClickedAir(p_41433_,p_41433_.getItemInHand(p_41434_));
            case OFF_HAND -> whenRightClickedAir(p_41433_,p_41433_.getItemInHand(p_41434_));
        }
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity sourceEntity, @NotNull LivingEntity targetEntity) {
        itemStack.hurtAndBreak(1, targetEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        whenAttacked(itemStack,sourceEntity,targetEntity);
        return super.hurtEnemy(itemStack, sourceEntity, targetEntity);
    }
}
