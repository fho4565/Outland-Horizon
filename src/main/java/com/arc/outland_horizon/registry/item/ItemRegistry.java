package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.OHBlocks;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.utils.ChatUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OutlandHorizon.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OutlandHorizon.MOD_ID);
    public static List<RegistryObject<Item>> ITEM_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MATERIAL_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> ARMOR_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> BLOCK_ITEM_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> CONSUMABLES_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> TOOL_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MAGIC_WEAPON_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> MELEE_WEAPON_LIST = new ArrayList<>();
    public static List<RegistryObject<Item>> RANGED_WEAPON_LIST = new ArrayList<>();


    public static RegistryObject<Item> registerItem(String id, Supplier<Item> item) {
        var object = ITEMS.register(id, item);
        ITEM_LIST.add(object);
        return object;
    }

    public static Item getItemRegistered(ResourceLocation resourceLocation) {
        return ITEM_LIST.stream().filter(object -> {
            if (object.getKey() != null) {
                return object.getKey().location().equals(resourceLocation);
            }
            return false;
        }).findFirst().orElseThrow().get();
    }

    public static void register(IEventBus bus) {
        OHItems.init();
        TABS.register(OutlandHorizon.MOD_ID + ".weapon.ranged", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".weapon.ranged"))
                .icon(() -> new ItemStack(OHItems.Weapon.MALICIOUS.get()))
                .displayItems((p, o) -> o.acceptAll(RANGED_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".weapon.melee", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".weapon.melee"))
                .icon(() -> new ItemStack(OHItems.Weapon.Melee.Sword.BLAZE_SWORD.get()))
                .displayItems((p, o) -> o.acceptAll(MELEE_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".weapon.magic", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".weapon.magic"))
                .icon(() -> new ItemStack(OHItems.Weapon.FIRE_WAND.get()))
                .displayItems((p, o) -> o.acceptAll(MAGIC_WEAPON_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".material", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".material"))
                .icon(() -> new ItemStack(OHItems.Material.NIGHTMARE_ENERGY.get()))
                .displayItems((p, o) -> o.acceptAll(MATERIAL_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".consumables", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".consumables"))
                .icon(() -> new ItemStack(OHItems.Consumable.MANA_POTION_COMMON.get()))
                .displayItems((p, o) -> o.acceptAll(CONSUMABLES_LIST.stream().map(itemRegistryObject ->
                        new ItemStack(itemRegistryObject.get())
                ).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".armor", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".armor"))
                .icon(() -> new ItemStack(OHItems.Armor.BLOOD_STONE_HELMET.get()))
                .displayItems((p, o) -> o.acceptAll(ARMOR_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".block", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".block"))
                .icon(() -> new ItemStack(OHBlocks.Natural.BLOOD_STONE_BLOCK.get()))
                .displayItems((p, o) -> o.acceptAll(BLOCK_ITEM_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(OutlandHorizon.MOD_ID + ".tool", () -> CreativeModeTab.builder()
                .title(ChatUtils.translatable("item_group." + OutlandHorizon.MOD_ID + ".tool"))
                .icon(() -> new ItemStack(OHItems.Tool.BLUE_GEM_PAXEL.get()))
                .displayItems((p, o) -> o.acceptAll(TOOL_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        TABS.register(bus);
        ITEMS.register(bus);
    }
}
