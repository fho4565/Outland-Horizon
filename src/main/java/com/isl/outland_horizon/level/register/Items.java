package com.isl.outland_horizon.level.register;

import com.isl.outland_horizon.item.weapons.weapon.melee.AAASword;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final HashMap<String, Supplier<Item>> simpleRegisterMap = new HashMap<>();
    public static final RegistryObject<Item> aaaSword = ITEM_DEFERRED_REGISTER.register("debug_sword", AAASword::new);
    public static final HashMap<String,RegistryObject<Item>> ITEM_LIST = new HashMap<>();
    public static void init(){
        simpleRegisterMap.forEach((name, supplier) -> {
            ITEM_LIST.put(name, ITEM_DEFERRED_REGISTER.register(name, supplier));
        });
    }
}
