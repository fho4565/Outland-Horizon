package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.registry.item.weapons.Magic;
import com.arc.outland_horizon.registry.item.weapons.Melee;
import com.arc.outland_horizon.registry.item.weapons.Ranged;
import com.arc.outland_horizon.world.item.tools.multi.Destroyer;
import com.arc.outland_horizon.world.item.tools.multi.Hammer;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import com.arc.outland_horizon.world.item.tools.multi.Spade;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.arc.outland_horizon.registry.item.Tools.registerTool;

public class ItemRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static List<RegistryObject<Item>> ITEM_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MATERIAL_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> ARMOR_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> BLOCK_ITEM_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> CONSUMABLES_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> TOOL_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MAGIC_WEAPON_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MELEE_WEAPON_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> RANGED_WEAPON_LIST = new ArrayList<>();

    private static void initVanillaExtends(String nameWithPrefix, Tier tier) {
        Tier nTier = new Tier() {
            @Override
            public int getUses() {
                return Math.round(tier.getUses() * 2.5f);
            }

            @Override
            public float getSpeed() {
                return tier.getSpeed() * 0.9f;
            }

            @Override
            public float getAttackDamageBonus() {
                return tier.getAttackDamageBonus();
            }

            @Override
            public int getLevel() {
                return tier.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return tier.getEnchantmentValue();
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return tier.getRepairIngredient();
            }
        };
        registerTool(nameWithPrefix + "_paxel", () -> new Paxel(nTier, new Item.Properties()));
        registerTool(nameWithPrefix + "_hammer", () -> new Hammer(tier, new Item.Properties()));
        registerTool(nameWithPrefix + "_spade", () -> new Spade(tier, new Item.Properties()));
        registerTool(nameWithPrefix + "_destroyer", () -> new Destroyer(nTier, new Item.Properties()));
    }
    public static RegistryObject<Item> registerItem(String id, Supplier<Item> item) {
        var object = ITEMS.register(id, item);
        ITEM_LIST.add(object);
        return object;
    }
    public static RegistryObject<Item> getItemRegistered(ResourceLocation resourceLocation) {
        return ITEM_LIST.stream().filter(itemRegistryObject -> {
            if (itemRegistryObject.getKey() != null) {
                return itemRegistryObject.getKey().location().equals(resourceLocation);
            }
            return false;
        }).findFirst().orElseThrow();
    }
    public static void register(IEventBus bus) {
        Tier nTier = new Tier() {
            @Override
            public int getUses() {
                return Math.round(Tiers.WOOD.getUses() * 2.5f);
            }

            @Override
            public float getSpeed() {
                return Tiers.WOOD.getSpeed() * 0.9f;
            }

            @Override
            public float getAttackDamageBonus() {
                return Tiers.WOOD.getAttackDamageBonus();
            }

            @Override
            public int getLevel() {
                return Tiers.WOOD.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return Tiers.WOOD.getEnchantmentValue();
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Tiers.WOOD.getRepairIngredient();
            }
        };
        registerTool("wooden_paxel", () -> new Paxel(nTier, new Item.Properties()));
        initVanillaExtends("golden", Tiers.GOLD);
        initVanillaExtends("stone", Tiers.STONE);
        initVanillaExtends("iron", Tiers.IRON);
        initVanillaExtends("diamond", Tiers.DIAMOND);
        initVanillaExtends("netherite", Tiers.NETHERITE);
        TABS.register(Utils.MOD_ID+".weapon.ranged", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".weapon.ranged"))
                .icon(() -> new ItemStack(Ranged.MALICIOUS.get()))
                .displayItems((p, o) -> o.acceptAll(RANGED_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".weapon.melee", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".weapon.melee"))
                .icon(() -> new ItemStack(Melee.BLAZE_SWORD.get()))
                .displayItems((p, o) -> o.acceptAll(MELEE_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".weapon.magic", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".weapon.magic"))
                .icon(() -> new ItemStack(Magic.FIRE_WAND.get()))
                .displayItems((p, o) -> o.acceptAll(MAGIC_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".material", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".material"))
                .icon(() -> new ItemStack(Materials.NIGHTMARE_ENERGY.get()))
                .displayItems((p, o) -> o.acceptAll(MATERIAL_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".consumables", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".consumables"))
                .icon(() -> new ItemStack(Consumables.MANA_POTION.get()))
                .displayItems((p, o) -> o.acceptAll(CONSUMABLES_LIST.stream().map(itemRegistryObject ->
                    new ItemStack(itemRegistryObject.get())
                ).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".armor", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".armor"))
                .icon(() -> new ItemStack(ItemRegistry.getItemRegistered(Utils.createResourceLocation("blood_stone_helmet")).get()))
                .displayItems((p, o) -> o.acceptAll(ARMOR_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".block", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".block"))
                .icon(() -> new ItemStack(getItemRegistered(Utils.createResourceLocation("blood_stone_block")).get()))
                .displayItems((p, o) -> o.acceptAll(BLOCK_ITEM_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(Utils.MOD_ID+".tool", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".tool"))
                .icon(() -> new ItemStack(getItemRegistered(Utils.createResourceLocation("blood_stone_paxel")).get()))
                .displayItems((p, o) -> o.acceptAll(TOOL_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(bus);
        ITEMS.register(bus);
    }
}
