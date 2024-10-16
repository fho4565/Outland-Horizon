package com.arc.outland_horizon.world.item.weapons.weapon.melee;

import com.google.common.collect.Multimap;
import com.arc.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMeleeWeapon extends AbstractWeapon {
    public AbstractMeleeWeapon(int maxDamage, float meleeAttackDamage, float attackSpeed, int enchantAbility, Item repairIngredient) {
        super(maxDamage, meleeAttackDamage,attackSpeed, enchantAbility, repairIngredient);
    }
    public AbstractMeleeWeapon(int maxDamage, float meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        super(maxDamage, meleeAttackDamage,-2.4f, enchantAbility, repairIngredient);
    }
    public AbstractMeleeWeapon(Tier tier, float meleeAttackDamage, float attackSpeed) {
        super(tier, meleeAttackDamage,attackSpeed);
    }
    @Override
    public boolean canAttackBlock(@NotNull BlockState p_41441_, @NotNull Level p_41442_, @NotNull BlockPos p_41443_, Player p_41444_) {
        return !p_41444_.isCreative();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }
}
