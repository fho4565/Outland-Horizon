package com.isl.outland_horizon.world;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ModArmorMaterials {
    public static ArmorMaterial BLOOD_STONE = create("blood_stone", 3,"blood_Stone");
    private static ArmorMaterial create(String name, float level,String itemName){
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * Math.round(8 * level);
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return new int[]{Math.round(2 * level), Math.round(5 * level), Math.round(6 * level), Math.round(2 * level)}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return Math.round(9 * level);
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_DIAMOND;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation(itemName)).get());
            }

            @Override
            public @NotNull String getName() {
                return name + "_armor";
            }

            @Override
            public float getToughness() {
                return level >= 2 ? level : 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return level >= 3 ? 0.1f * level / 3.0f : 0f;
            }
        };
    }
    public static @NotNull ArmorMaterial createArmorMaterial(String name, float level, String finalNameWithPrefix) {
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * Math.round(8 * level);
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return new int[]{Math.round(2 * level), Math.round(5 * level), Math.round(6 * level), Math.round(2 * level)}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return Math.round(9 * level);
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_DIAMOND;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(ItemRegistry.getItemRegistered(Utils.createResourceLocation(finalNameWithPrefix)).get());
            }

            @Override
            public @NotNull String getName() {
                return name + "_armor";
            }

            @Override
            public float getToughness() {
                return level >= 2 ? level : 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return level >= 3 ? 0.1f * level / 3.0f : 0f;
            }
        };
    }
}
