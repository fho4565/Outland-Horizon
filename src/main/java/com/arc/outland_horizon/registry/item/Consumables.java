package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.world.item.consumables.potions.ManaPotion;
import com.arc.outland_horizon.world.item.consumables.potions.RagePotion;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Consumables {
    public static final RegistryObject<Item> MANA_POTION = registerConsumables("mana_potion", ManaPotion::new);
    public static final RegistryObject<Item> RAGE_POTION = registerConsumables("rage_potion", RagePotion::new);

    public static RegistryObject<Item> registerConsumables(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.CONSUMABLES_LIST.add(object);
        return object;
    }

    public static void init() {

    }
}
