package com.isl.outland_horizon.world.item.registry;

import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Armors {

    public static RegistryObject<Item> registerArmor(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.ARMOR_LIST.add(object);
        return object;
    }
    public static void init(){}
}
