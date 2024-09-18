package com.isl.outland_horizon.world.item.registry.weapons;

import com.isl.outland_horizon.world.item.ItemRegistry;
import com.isl.outland_horizon.world.item.weapons.weapon.magic.wand.FireWand;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Magic {
    public static final RegistryObject<Item> FIRE_WAND = registerWeaponMagic("fire_wand", FireWand::new);
    public static RegistryObject<Item> registerWeaponMagic(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.MAGIC_WEAPON_LIST.add(object);
        return object;
    }
    public static void init(){}
}
