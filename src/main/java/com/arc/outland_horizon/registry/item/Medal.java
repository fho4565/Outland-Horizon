package com.arc.outland_horizon.registry.item;

import com.arc.outland_horizon.world.item.medal.ZombieMedal;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class Medal {
    public static final RegistryObject<Item> ZOMBIE_MEDAL_COPPER = Tools.registerTool("zombie_medal_copper", ZombieMedal.Copper::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_SILVER = Tools.registerTool("zombie_medal_silver", ZombieMedal.Silver::new);
    public static final RegistryObject<Item> ZOMBIE_MEDAL_GOLD = Tools.registerTool("zombie_medal_gold", ZombieMedal.Gold::new);
    public static void init(){}
}
