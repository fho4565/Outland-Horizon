package com.isl.oh.registry;

import com.isl.oh.utils.Utils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    public static void register(String id, Supplier<Block> block) {
        register(id, block, true);
    }

    public static void register(String id, Supplier<Block> block, boolean blockItem) {
        var object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.register(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
    }
}
