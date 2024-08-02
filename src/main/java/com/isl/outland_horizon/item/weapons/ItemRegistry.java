package com.isl.outland_horizon.item.weapons;

import com.google.common.collect.Sets;
import com.isl.outland_horizon.item.weapons.magic.wand.FireWand;
import com.isl.outland_horizon.item.weapons.weapon.melee.AAASword;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);

    public static final Set<RegistryObject<Item>> items = Sets.newHashSet();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> AAA_SWORD = register("debug_sword", AAASword::new);
    public static final RegistryObject<Item> fw = register("fire_wand", FireWand::new);

    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        var object = ITEMS.register(id, item);
        items.add(object);
        return object;
    }

    public static void register(IEventBus bus) {
        TABS.register("main", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".main"))
                .icon(() -> new ItemStack(Items.APPLE))
                .displayItems((p, o) -> o.acceptAll(items.stream().map(i -> new ItemStack(i.get())).collect(Collectors.toSet())))
                .build());
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
