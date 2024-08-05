package com.isl.outland_horizon.world.item;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.consumables.AbstractConsumables;
import com.isl.outland_horizon.world.item.weapons.wand.FireWand;
import com.isl.outland_horizon.world.item.weapons.weapon.melee.AAASword;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.isl.outland_horizon.world.block.BlockRegistry.NIGHTMARE_LOG;

public class ItemRegistry {
    public static List<RegistryObject<Item>> ITEM_LIST = new ArrayList<>();

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> BLOOD_BUCKET = register("blood_bucket", () -> new BucketItem(BlockRegistry.FluidRegistry.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_SWORD = register("debug_sword", AAASword::new);
    public static final RegistryObject<Item> FIRE_WAND = register("fire_wand", FireWand::new);
    public static final RegistryObject<Item> MANA_POTION = register("mana_potion", AbstractConsumables::new);

    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        var object = ITEMS.register(id, item);
        ITEM_LIST.add(object);
        return object;
    }

    public static void register(IEventBus bus) {
        TABS.register("main", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".main"))
                .icon(() -> new ItemStack(Items.APPLE))
                .displayItems((p, o) -> o.acceptAll(ITEM_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
