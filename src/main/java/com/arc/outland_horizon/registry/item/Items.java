package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.registry.block.Functional;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final RegistryObject<Item> DUNGEON_TORCH = BlockItems.registerBlockItem("dungeon_torch", () -> new StandingAndWallBlockItem(Functional.DUNGEON_TORCH.get(), Functional.WALL_DUNGEON_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static void init(){}
}
