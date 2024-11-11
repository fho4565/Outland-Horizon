package com.arc.outland_horizon;

import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ModArmorMaterials {
    public static ArmorMaterial BLOOD_STONE = createArmorMaterial("blood_stone", 3, Ingredient.of(OHItems.Material.BLOOD_STONE.get()));
    public static ArmorMaterial BLUE_GEM = createArmorMaterial("blue_gem", 2, Ingredient.of(OHItems.Material.BLUE_GEM.get()));

    /*public static ArmorMaterial VOID_WASTE = createArmorMaterial("void_waste", ModTiers.VOID_WASTE_LEVEL,"void_waste");
    public static ArmorMaterial MITHRIL = createArmorMaterial("mithril", ModTiers.MITHRIL_LEVEL,"mithril");
    public static ArmorMaterial GAMMA = createArmorMaterial("gamma_energy_battery", ModTiers.GAMMA_LEVEL,"gamma_energy_battery");
    public static ArmorMaterial CONDENSE = createArmorMaterial("condense", ModTiers.CONDENSE_LEVEL,"condense");
    public static ArmorMaterial VOID = createArmorMaterial("void", ModTiers.VOID_CRYSTAL_LEVEL,"void");
    */
    public static @NotNull ArmorMaterial createArmorMaterial(String name, float level, Ingredient repairIngredient) {
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return new int[]{13, 18, 25, 11}[type.getSlot().getIndex()] * Math.round(14 * level);
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return new int[]{Math.round(1.5f * level), Math.round(3 * level), Math.round(4 * level), Math.round(1.5f * level)}[type.getSlot().getIndex()];
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
                return repairIngredient;
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
