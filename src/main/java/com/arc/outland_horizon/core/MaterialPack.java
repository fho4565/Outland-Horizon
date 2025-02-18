package com.arc.outland_horizon.core;

import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class MaterialPack {
    final Map<ItemVariant, Item> itemVariant = Maps.newHashMap();
    final Map<BlockVariant, Block> blockVariant = Maps.newHashMap();
    float exp = 0.1f;
    int time = 200;

    public Item get(ItemVariant itemVariant) {
        return this.itemVariant.get(itemVariant);
    }

    public Block get(BlockVariant itemVariant) {
        return this.blockVariant.get(itemVariant);
    }

    public float exp() {
        return exp;
    }

    public int time() {
        return time;
    }

    public enum ItemVariant {
        DUST("dust"),
        GEM("gem"),
        NUGGET("nugget"),
        INGOT("ingot"),
        SWORD("sword"),
        PICKAXE("pickaxe"),
        AXE("axe"),
        SHOVEL("shovel"),
        HOE("hoe"),
        PAXEL("paxel"),
        SPADE("spade"),
        HAMMER("hammer"),
        DESTROYER("destroyer"),
        HELMET("helmet"),
        CHESTPLATE("chestplate"),
        LEGGINGS("leggings"),
        BOOTS("boots");
        private final String name;

        ItemVariant(String pVariantName) {
            this.name = pVariantName;
        }

        public String getName() {
            return this.name;
        }
    }

    public enum BlockVariant {
        ORE("ore"),
        DEEP_ORE("deep_ore"),
        BLOCK("Block");
        private final String name;

        BlockVariant(String pVariantName) {
            this.name = pVariantName;
        }

        public String getName() {
            return this.name;
        }
    }

    MaterialPack() {
    }


    public static class Builder {
        private final MaterialPack family;

        Builder() {
            this.family = new MaterialPack();
        }

        public static Builder of() {
            return new Builder();
        }

        public MaterialPack getPack() {
            return this.family;
        }

        public MaterialPack.Builder dust(Item dustItem) {
            this.family.itemVariant.remove(ItemVariant.GEM);
            this.family.itemVariant.remove(ItemVariant.INGOT);
            this.family.itemVariant.remove(ItemVariant.NUGGET);
            this.family.itemVariant.put(ItemVariant.DUST, dustItem);
            return this;
        }

        public MaterialPack.Builder smeltingTime(int time) {
            this.family.time = time;
            return this;
        }

        public MaterialPack.Builder smeltExp(float exp) {
            this.family.exp = exp;
            return this;
        }

        public MaterialPack.Builder nugget(Item nuggetItem) {
            this.family.itemVariant.remove(ItemVariant.GEM);
            this.family.itemVariant.remove(ItemVariant.INGOT);
            this.family.itemVariant.remove(ItemVariant.DUST);
            this.family.itemVariant.put(ItemVariant.NUGGET, nuggetItem);
            return this;
        }

        public MaterialPack.Builder gem(Item gemItem) {
            this.family.itemVariant.remove(ItemVariant.DUST);
            this.family.itemVariant.remove(ItemVariant.INGOT);
            this.family.itemVariant.remove(ItemVariant.NUGGET);
            this.family.itemVariant.put(ItemVariant.GEM, gemItem);
            return this;
        }

        public MaterialPack.Builder ingot(Item ingotItem) {
            this.family.itemVariant.remove(ItemVariant.GEM);
            this.family.itemVariant.remove(ItemVariant.DUST);
            this.family.itemVariant.remove(ItemVariant.NUGGET);
            this.family.itemVariant.put(ItemVariant.INGOT, ingotItem);
            return this;
        }

        public MaterialPack.Builder sword(Item swordItem) {
            this.family.itemVariant.put(ItemVariant.SWORD, swordItem);
            return this;
        }

        public MaterialPack.Builder pickaxe(Item pickaxeItem) {
            this.family.itemVariant.put(ItemVariant.PICKAXE, pickaxeItem);
            return this;
        }

        public MaterialPack.Builder axe(Item axeItem) {
            this.family.itemVariant.put(ItemVariant.AXE, axeItem);
            return this;
        }

        public MaterialPack.Builder shovel(Item shovelItem) {
            this.family.itemVariant.put(ItemVariant.SHOVEL, shovelItem);
            return this;
        }

        public MaterialPack.Builder hoe(Item hoeItem) {
            this.family.itemVariant.put(ItemVariant.HOE, hoeItem);
            return this;
        }

        public MaterialPack.Builder paxel(Item paxelItem) {
            this.family.itemVariant.put(ItemVariant.PAXEL, paxelItem);
            return this;
        }

        public MaterialPack.Builder spade(Item spadeItem) {
            this.family.itemVariant.put(ItemVariant.SPADE, spadeItem);
            return this;
        }

        public MaterialPack.Builder hammer(Item hammerItem) {
            this.family.itemVariant.put(ItemVariant.HAMMER, hammerItem);
            return this;
        }

        public MaterialPack.Builder destroyer(Item destroyerItem) {
            this.family.itemVariant.put(ItemVariant.DESTROYER, destroyerItem);
            return this;
        }

        public MaterialPack.Builder helmet(Item helmetItem) {
            this.family.itemVariant.put(ItemVariant.HELMET, helmetItem);
            return this;
        }

        public MaterialPack.Builder chestplate(Item chestplateItem) {
            this.family.itemVariant.put(ItemVariant.CHESTPLATE, chestplateItem);
            return this;
        }

        public MaterialPack.Builder leggings(Item leggingsItem) {
            this.family.itemVariant.put(ItemVariant.LEGGINGS, leggingsItem);
            return this;
        }

        public MaterialPack.Builder boots(Item bootsItem) {
            this.family.itemVariant.put(ItemVariant.BOOTS, bootsItem);
            return this;
        }

        public MaterialPack.Builder block(Block gemItem) {
            this.family.blockVariant.put(BlockVariant.BLOCK, gemItem);
            return this;
        }

        public MaterialPack.Builder ore(Block gemItem) {
            this.family.blockVariant.put(BlockVariant.ORE, gemItem);
            return this;
        }

        public MaterialPack.Builder deepOre(Block ingotItem) {
            this.family.blockVariant.put(BlockVariant.DEEP_ORE, ingotItem);
            return this;
        }
    }
}
