package com.arc.outland_horizon.core;

import com.arc.outland_horizon.registry.OHItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ModArmorMaterials {
    public static final ArmorMaterial BLOOD_STONE = createArmorMaterial("blood_stone", ModTiers.BLOOD_STONE_LEVEL, Ingredient.of(OHItems.Material.BLOOD_STONE.get()));
    public static final ArmorMaterial BLUE_GEM = createArmorMaterial("blue_gem", ModTiers.BLUE_GEM_LEVEL, Ingredient.of(OHItems.Material.BLUE_GEM.get()));

    /*public static ArmorMaterial VOID_WASTE = createArmorMaterial("void_waste", ModTiers.VOID_WASTE_LEVEL,"void_waste");
    public static ArmorMaterial MITHRIL = createArmorMaterial("mithril", ModTiers.MITHRIL_LEVEL,"mithril");
    public static ArmorMaterial GAMMA = createArmorMaterial("gamma_energy_battery", ModTiers.GAMMA_LEVEL,"gamma_energy_battery");
    public static ArmorMaterial CONDENSE = createArmorMaterial("condense", ModTiers.CONDENSE_LEVEL,"condense");
    public static ArmorMaterial VOID = createArmorMaterial("void", ModTiers.VOID_CRYSTAL_LEVEL,"void");
    */
    public static @NotNull ArmorMaterial createArmorMaterial(String name, double level, Ingredient repairIngredient) {
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return (new int[]{8, 11, 14, 7}[type.getSlot().getIndex()] * (int) Math.round(14 * level));
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{(int) Math.round(1.1f * level), (int) Math.round(2.5 * level), (int) Math.round(3 * level), (int) Math.round(level)}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return (int) Math.round(4 * level);
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
                return level >= 2 ? (float) level : 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return level >= 3 ? (0.1f * (float) level / 3.0f) : 0f;
            }
        };
    }
}
