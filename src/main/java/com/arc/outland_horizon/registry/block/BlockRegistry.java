package com.arc.outland_horizon.registry.block;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.registry.item.BlockItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BlockRegistry {
    public static List<RegistryObject<Block>> BLOCK_LIST = new ArrayList<>();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return register(id, block, true);
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem) {
        RegistryObject<Block> object = BLOCKS.register(id, block);
        if (blockItem) {
            BlockItems.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties()));
        }
        BLOCK_LIST.add(object);
        return object;
    }
    public static RegistryObject<Block> register(String id, Supplier<Block> block, boolean blockItem,Rarity rarity) {
        RegistryObject<Block> object = BLOCKS.register(id, block);
        if (blockItem) {
            BlockItems.registerBlockItem(id, () -> new BlockItem(object.get(), new Item.Properties().rarity(rarity)));
        }
        BLOCK_LIST.add(object);
        return object;
    }
    public static Block getBlockRegistered(ResourceLocation resourceLocation) {
        Optional<RegistryObject<Block>> first = BLOCK_LIST.stream().filter(blockRegistryObject -> blockRegistryObject.getKey() != null)
                .filter(blockRegistryObject -> blockRegistryObject.getId().equals(resourceLocation))
                .findFirst();
        if(first.isPresent()) {
            return first.get().get();
        }
        throw new RuntimeException("NULL");
    }
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
