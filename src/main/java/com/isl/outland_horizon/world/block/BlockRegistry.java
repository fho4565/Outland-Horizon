package com.isl.outland_horizon.world.block;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.item.registry.BlockItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {
    public static List<RegistryObject<Block>> BLOCK_LIST = new ArrayList<>();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return register(id, block, true);
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem) {
        var object = BLOCKS.register(id, block);
        if (blockItem) {
            BlockItems.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
        return object;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
