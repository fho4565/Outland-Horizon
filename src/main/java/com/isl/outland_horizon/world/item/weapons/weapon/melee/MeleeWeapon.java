package com.isl.outland_horizon.world.item.weapons.weapon.melee;

import com.google.common.collect.Multimap;
import com.isl.outland_horizon.world.item.weapons.weapon.AbstractWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MeleeWeapon extends AbstractWeapon {
    protected MeleeWeapon(int maxDamage, int meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        super(maxDamage, meleeAttackDamage, enchantAbility, repairIngredient);
        this.getDamage = ()-> this.defaultModifiers.get(Attributes.ATTACK_DAMAGE).stream()
                .filter((attributeModifier) -> attributeModifier.getId().equals(BASE_ATTACK_DAMAGE_UUID))
                .findFirst().map(attributeModifier -> (float) attributeModifier.getAmount()).orElse(1F);
    }
    protected MeleeWeapon(Tier tier, int meleeAttackDamage) {
        super(tier, meleeAttackDamage);
    }
    public static MeleeWeapon of(int maxDamage, int meleeAttackDamage, int enchantAbility, Item repairIngredient) {
        return new MeleeWeapon(maxDamage,  meleeAttackDamage,  enchantAbility, repairIngredient);
    }
    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
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
