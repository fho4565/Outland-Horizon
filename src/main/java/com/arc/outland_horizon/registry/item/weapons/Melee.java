package com.arc.outland_horizon.registry.item.weapons;

import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.world.item.weapons.weapon.melee.AAASword;
import com.arc.outland_horizon.world.item.weapons.weapon.melee.BlazeSword;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Melee {
    public static final RegistryObject<Item> DEBUG_SWORD = registerWeaponMelee("debug_sword", AAASword::new);
    public static final RegistryObject<Item> BLAZE_SWORD = registerWeaponMelee("blaze_sword", BlazeSword::new);
    public static RegistryObject<Item> registerWeaponMelee(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MELEE_WEAPON_LIST.add(object);
        return object;
    }
    public static void init(){}
}
