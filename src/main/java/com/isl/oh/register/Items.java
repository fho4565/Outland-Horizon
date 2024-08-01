package com.isl.oh.register;

import com.isl.oh.Utils;
import com.isl.oh.items.weapons.melee.sword.AAASword;
import com.isl.oh.quicktools.MaterialPackage;
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
    public static void init(){
        new MaterialPackage("a1", MaterialPackage.MaterialType.DUST, 1).create();
        new MaterialPackage("a2", MaterialPackage.MaterialType.DUST, 2).create();
        new MaterialPackage("a3", MaterialPackage.MaterialType.DUST, 3).create();
        simpleRegisterMap.forEach(ITEM_DEFERRED_REGISTER::register);
    }
}
