package com.isl.outland_horizon.world.item.registry;

import com.isl.outland_horizon.world.block.Fluid;
import com.isl.outland_horizon.world.item.ItemRegistry;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Tools {

    public static final RegistryObject<Item> BLOOD_BUCKET = registerTool("blood_bucket", () -> new BucketItem(Fluid.FluidRegistry.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static RegistryObject<Item> registerTool(String id, Supplier<Item> item) {
        RegistryObject<Item> object = ItemRegistry.registerItem(id, item);
        ItemRegistry.TOOL_LIST.add(object);
        return object;
    }
    public static void init(){}
}
