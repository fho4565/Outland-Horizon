package com.isl.oh.register;

import com.isl.oh.Utils;
import com.isl.oh.items.weapons.melee.sword.AAASword;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> aaaSword = ITEM_DEFERRED_REGISTER.register("aaa", AAASword::new);

}
