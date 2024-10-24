package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.world.item.consumables.potions.ManaPotion;
import com.arc.outland_horizon.world.item.consumables.potions.MadPotion;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Consumables {
    public static final RegistryObject<Item> MANA_POTIONCommon = registerConsumables("mana_potion.common", ManaPotion.Common::new);
    public static final RegistryObject<Item> MANA_POTIONIntermediate = registerConsumables("mana_potion.intermediate", ManaPotion.Intermediate::new);
    public static final RegistryObject<Item> MANA_POTIONAdvanced = registerConsumables("mana_potion.advanced", ManaPotion.Advanced::new);
    public static final RegistryObject<Item> MANA_POTIONSuper = registerConsumables("mana_potion.super", ManaPotion.Super::new);
    public static final RegistryObject<Item> MANA_POTIONUltimate = registerConsumables("mana_potion.ultimate", ManaPotion.Ultimate::new);
    public static final RegistryObject<Item> RAGE_POTION = registerConsumables("mad_potion", MadPotion::new);

    public static RegistryObject<Item> registerConsumables(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.CONSUMABLES_LIST.add(object);
        return object;
    }

    public static void init() {

    }
}
