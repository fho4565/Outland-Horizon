package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.registry.item.Armors;
import com.arc.outland_horizon.registry.item.Materials;
import com.arc.outland_horizon.world.ModArmorMaterials;
import com.arc.outland_horizon.world.ModTiers;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class MaterialPack {
    public enum MaterialType {
        GEM, DUST, INGOT, CUSTOM
    }
    private Tier tier;
    private ArmorMaterial armorMaterial;

    private MaterialPack() {
    }
    public static void create(MaterialType type, String name, float level,boolean dropBlockWhenOreBroken) {
        MaterialPack materialPack = new MaterialPack();
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                Materials.registerMaterial(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                Materials.registerMaterial(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                Materials.registerMaterial(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                Materials.registerMaterial(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        materialPack.tier = ModTiers.createTier(level, nameWithPrefix);
        materialPack.armorMaterial = ModArmorMaterials.createArmorMaterial(name, level, nameWithPrefix);
        ToolPack.create(type, name, materialPack.tier, level,dropBlockWhenOreBroken);
        Armors.registerArmor(nameWithPrefix + "_helmet", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.HELMET, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_chestplate", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_leggings", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_boots", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.BOOTS, new Item.Properties()));
    }

    public static void create(MaterialType type, String name,Tier tier,ArmorMaterial armorMaterial, float level,boolean dropBlockWhenOreBroken) {
        MaterialPack materialPack = new MaterialPack();
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                Materials.registerMaterial(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                Materials.registerMaterial(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                Materials.registerMaterial(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                Materials.registerMaterial(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        materialPack.tier = tier;
        materialPack.armorMaterial = armorMaterial;
        ToolPack.create(type, name, tier, level,dropBlockWhenOreBroken);
        Armors.registerArmor(nameWithPrefix + "_helmet", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.HELMET, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_chestplate", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_leggings", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        Armors.registerArmor(nameWithPrefix + "_boots", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.BOOTS, new Item.Properties()));
    }
}
