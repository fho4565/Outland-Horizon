package com.isl.oh.quicktools;

import com.isl.oh.register.Blocks;
import com.isl.oh.register.Items;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

/**
 * 快速创建简单材料对应的工具，物品，方块和盔甲的工具类。<br>
 * 不创建材质，json合成配方，需自备。<br>
 * 工具包创建的物品注册名为材料包名字_类型，比如类型为DUST的基础合成材料注册名为"工具包名字_dust"。
 * */
public class MaterialPackage {
    /**
     * 此材料包创建时的材料类型
     */
    public enum MaterialType{
        /**
         * 宝石类型，例如原版钻石
         */
        GEM,
        /**
         * 锭类型，例如原版铁锭
         */
        INGOT,
        /**
         * 粉末类型，例如工业模组的矿石粉
         */
        DUST
    }
    private final String name;
    private final MaterialType type;
    Tier tier;
    ArmorMaterial armorMaterial;
    /**
     * 创建一个材料包
     * @param name 材料包名字
     * @param type 材料类型
     * @param level 材料包等级，相对于铁制工具的等级,为0时耐久度等为0<br>
     */
    public static void create(String name, MaterialType type, float level) {
        MaterialPackage materialPackage = new MaterialPackage(name, type, level);
        Tier tier = materialPackage.tier;
        ArmorMaterial armorMaterial = materialPackage.armorMaterial;
        switch (type){
            case GEM -> Items.simpleRegisterMap.put(name+"_gem",()->new Item(new Item.Properties()));
            case INGOT -> Items.simpleRegisterMap.put(name+"_ingot",()->new Item(new Item.Properties()));
            case DUST -> Items.simpleRegisterMap.put(name+"_dust",()->new Item(new Item.Properties()));
        }
        Items.simpleRegisterMap.put(name+"_block", ()->new BlockItem(Blocks.BLOCK_LIST.get(name+"_block").get(), new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_ore",()->new BlockItem(Blocks.BLOCK_LIST.get(name+"_ore").get(), new Item.Properties()));
        Blocks.simpleRegisterMap.put(name+"_ore",()->new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.25F,1).sound(SoundType.STONE)));
        Blocks.simpleRegisterMap.put(name+"_block",()->new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.2F,1).sound(SoundType.METAL)));
        Items.simpleRegisterMap.put(name+"_sword",()->new SwordItem(tier,3,-2.4f,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_pickaxe",()->new PickaxeItem(tier,1,-2.8f,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_axe",()->new AxeItem(tier,6,-3.1f,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_shovel",()->new ShovelItem(tier,1.5f,-3.0f,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_hoe",()->new HoeItem(tier,-2,-1f,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_helmet",()->new ArmorItem(armorMaterial,ArmorItem.Type.HELMET,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_chestplate",()->new ArmorItem(armorMaterial,ArmorItem.Type.CHESTPLATE,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_leggings",()->new ArmorItem(armorMaterial,ArmorItem.Type.LEGGINGS,new Item.Properties()));
        Items.simpleRegisterMap.put(name+"_boots",()->new ArmorItem(armorMaterial,ArmorItem.Type.BOOTS,new Item.Properties()));
    }
    private MaterialPackage(String name, MaterialType type, double level) {
        this.name = name;
        this.type = type;
        this.tier = new Tier() {
            @Override
            public int getUses() {
                return (int) Math.round((250*level));
            }

            @Override
            public float getSpeed() {
                return (float) (6+3*level);
            }

            @Override
            public float getAttackDamageBonus() {
                return (float) Math.pow(2,level-1);
            }

            @Override
            public int getLevel() {
                return (int) level;
            }

            @Override
            public int getEnchantmentValue() {
                return (int) Math.round(level*9);
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return switch (type) {
                    case GEM -> Ingredient.of(Items.simpleRegisterMap.get(name + "_gem").get());
                    case INGOT -> Ingredient.of(Items.simpleRegisterMap.get(name + "_ingot").get());
                    case DUST -> Ingredient.of(Items.simpleRegisterMap.get(name + "_dust").get());
                };
            }
        };
        this.armorMaterial = new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * (int) Math.round(15*level);
            }
            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return new int[]{(int) Math.round(level*2), (int) Math.round(level*5), (int) Math.round(level*6), (int) Math.round(level*2)}[type.getSlot().getIndex()];

            }
            @Override
            public int getEnchantmentValue() {
                return (int) Math.round(level*9);
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_IRON;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return switch (type) {
                    case GEM -> Ingredient.of(Items.simpleRegisterMap.get(name + "_gem").get());
                    case INGOT -> Ingredient.of(Items.simpleRegisterMap.get(name + "_ingot").get());
                    case DUST -> Ingredient.of(Items.simpleRegisterMap.get(name + "_dust").get());
                };
            }

            @Override
            public @NotNull String getName() {
                return name + "_armor";
            }

            @Override
            public float getToughness() {
                return level>=3? (float) (level - 2) : 0;
            }

            @Override
            public float getKnockbackResistance() {
                return level>=4? (float) (level - 3) : 0;
            }
        };
    }
}
