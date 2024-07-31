package com.isl.oh.items.weapons;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    public AbstractWeapon(int maxDamage,int meleeAttackDamage,int enchantAbility,Item repairIngredient) {
        super(new Tier() {
            public int getUses() {
                return maxDamage;
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
    }
    public void whenAttacked(ItemStack itemStack, LivingEntity sourceEntity, LivingEntity targetEntity){}
    public void whenLeftClicked(Player player,ItemStack itemStack){}
    public void whenRightClicked(Player player,ItemStack itemStack){}
    public void whenHolding(Entity entity,ItemStack itemStack){}
    public void whenInInventory(Entity entity,ItemStack itemStack,int slotId){}

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
            case MAIN_HAND -> whenLeftClicked(p_41427_.getPlayer(),p_41427_.getItemInHand());
            case OFF_HAND -> whenRightClicked(p_41427_.getPlayer(),p_41427_.getItemInHand());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_41432_, @NotNull Player p_41433_, InteractionHand p_41434_) {
        switch (p_41434_) {
            case MAIN_HAND -> whenLeftClicked(p_41433_,p_41433_.getItemInHand(p_41434_));
            case OFF_HAND -> whenRightClicked(p_41433_,p_41433_.getItemInHand(p_41434_));
        }
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity sourceEntity, @NotNull LivingEntity targetEntity) {
        whenAttacked(itemStack,sourceEntity,targetEntity);
        return super.hurtEnemy(itemStack, sourceEntity, targetEntity);
    }

}
