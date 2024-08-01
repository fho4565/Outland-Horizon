package com.isl.outland_horizon.level.register;

import com.isl.outland_horizon.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class Blocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final HashMap<String, Supplier<Block>> simpleRegisterMap = new HashMap<>();
    public static final HashMap<String, RegistryObject<Block>> BLOCK_LIST = new HashMap<>();
    public static void init(){
        simpleRegisterMap.forEach((name, supplier) -> {
            BLOCK_LIST.put(name, BLOCK_DEFERRED_REGISTER.register(name, supplier));
        });
    }
}
