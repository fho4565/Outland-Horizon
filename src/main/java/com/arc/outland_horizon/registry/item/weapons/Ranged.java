package com.arc.outland_horizon.registry.item.weapons;

import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.registry.item.Materials;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun.FrequencyVariation;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun.Genocide;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun.Malicious;
import com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun.VoidImpact;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Ranged {
    public static final RegistryObject<Item> MALICIOUS = registerWeaponRanged("malicious", () -> new Malicious(450, 10, Materials.NIGHTMARE_ENERGY.get()));
    public static final RegistryObject<Item> FREQUENCY_VARIATION = registerWeaponRanged("frequency_variation", () -> new FrequencyVariation(375, 10, Items.DIAMOND));
    public static final RegistryObject<Item> PAO = registerWeaponRanged("genocide", () -> new Genocide(100, 10, Items.DIAMOND));
    public static final RegistryObject<Item> VOID_IMPACT = registerWeaponRanged("void_impact", () -> new VoidImpact(3500, 10, Items.DIAMOND));

    public static RegistryObject<Item> registerWeaponRanged(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.RANGED_WEAPON_LIST.add(object);
        return object;
    }
    public static void init(){}
}
