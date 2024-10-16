package com.arc.outland_horizon.registry.block;

import com.arc.outland_horizon.world.block.BlockRegistry;
import com.arc.outland_horizon.world.block.NightmareDirt;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class Natural {
    public static final RegistryObject<Block> NIGHTMARE_DIRT = BlockRegistry.register("nightmare_dirt", NightmareDirt::new);
    public static void init(){}
}
