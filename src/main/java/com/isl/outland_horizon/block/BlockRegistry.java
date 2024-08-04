package com.isl.outland_horizon.block;

import com.isl.outland_horizon.registry.ItemRegistry;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final HashMap<String, Supplier<Block>> simpleRegisterMapBlock = new HashMap<>();
    public static final HashMap<String, RegistryObject<Block>> BLOCK_LIST = new HashMap<>();

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return register(id, block, true);
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem) {
        var object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.register(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
        return object;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static void init(){
        simpleRegisterMapBlock.forEach((name, supplier) -> {
            BLOCK_LIST.put(name, BLOCKS.register(name, supplier));
        });
    }
}
