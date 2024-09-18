package com.isl.outland_horizon.world.item.registry;

import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Materials {
    public static final RegistryObject<Item> NIGHTMARE_ENERGY = registerMaterial("nightmare_energy", () -> new Item(new Item.Properties()));

    public static RegistryObject<Item> registerMaterial(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MATERIAL_LIST.add(object);
        return object;
    }
    public static void init(){}
}
