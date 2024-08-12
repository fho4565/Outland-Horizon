package com.isl.outland_horizon.utils;

import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import com.isl.outland_horizon.world.item.tools.multi.Hammer;
import com.isl.outland_horizon.world.item.tools.multi.Paxel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;


public class MaterialPack {
    public enum MaterialType {
        GEM, DUST, INGOT, CUSTOM
    }

    private final MaterialType type;
    private final String name;
    private final float level;
    private final Tier tier;
    private final ArmorMaterial armorMaterial;

    private MaterialPack(MaterialType type, String name, float level) {
        this.type = type;
        this.name = name;
        this.level = level;
        tier = new Tier() {
            @Override
            public int getUses() {
                return (int) (250 * level);
            }

            @Override
            public float getSpeed() {
                return Math.round(4.5 * level);
            }

            @Override
            public float getAttackDamageBonus() {
                return Math.round(level);
            }

            @Override
            public int getLevel() {
                return (int) level;
            }

            @Override
            public int getEnchantmentValue() {
                return (int) (7 * level);
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.APPLE);
            }
        };
        armorMaterial = new ArmorMaterial() {
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
                return Ingredient.of(new ItemStack(Blocks.WATER));
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

    public static void create(MaterialType type, String name, float level) {
        MaterialPack materialPack = new MaterialPack(type, name, level);
        String nameWithPrefix = name;
        switch (type) {
            case GEM -> {
                ItemRegistry.register(name + "_gem", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_gem";
            }
            case DUST -> {
                ItemRegistry.register(name + "_dust", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_dust";
            }
            case INGOT -> {
                ItemRegistry.register(name + "_ingot", () -> new Item(new Item.Properties()));
                nameWithPrefix = name + "_ingot";
            }
            case CUSTOM -> {
                ItemRegistry.register(name, () -> new Item(new Item.Properties()));
                nameWithPrefix = name;
            }
        }
        BlockRegistry.register(nameWithPrefix + "_block", () -> new Block(Block.Properties.of().sound(SoundType.METAL).strength(0.15f * level, 0.3f * level).requiresCorrectToolForDrops()));
        BlockRegistry.register(nameWithPrefix + "_ore", () -> new Block(Block.Properties.of().sound(SoundType.STONE).strength(0.2f * level, 0.2f * level).requiresCorrectToolForDrops()));
        ItemRegistry.register(nameWithPrefix + "_sword", () -> new SwordItem(materialPack.tier, 3, -2.4f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_pickaxe", () -> new PickaxeItem(materialPack.tier, 1, -2.8f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_axe", () -> new AxeItem(materialPack.tier, 6, -3.1f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_shovel", () -> new ShovelItem(materialPack.tier, 1.5f, -3.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hoe", () -> new HoeItem(materialPack.tier, -2, -1.0f, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_paxel", () -> new Paxel(materialPack.tier,  new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hammer", () -> new Hammer(materialPack.tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_helmet", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.HELMET, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_chestplate", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_leggings", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_boots", () -> new ArmorItem(materialPack.armorMaterial, ArmorItem.Type.BOOTS, new Item.Properties()));
    }
}
