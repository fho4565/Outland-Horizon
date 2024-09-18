package com.isl.outland_horizon.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class Natural {
    public static final RegistryObject<Block> NIGHTMARE_DIRT = BlockRegistry.register("nightmare_dirt",NightmareDirt::new);
    public static void init(){}
}
