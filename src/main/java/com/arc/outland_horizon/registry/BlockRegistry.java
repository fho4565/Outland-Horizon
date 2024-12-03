package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OutlandHorizon.MOD_ID);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return register(id, block, true);
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem) {
        RegistryObject<Block> object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
        return object;
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem, boolean autoAddToTab) {
        RegistryObject<Block> object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties()), autoAddToTab);
        }
        return object;
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem, Rarity rarity) {
        RegistryObject<Block> object = BLOCKS.register(id, block);
        if (blockItem) {
            ItemRegistry.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties().rarity(rarity)));
        }
        return object;
    }

    public static void register(IEventBus bus) {
        OHBlocks.init();
        BLOCKS.register(bus);
    }
}
