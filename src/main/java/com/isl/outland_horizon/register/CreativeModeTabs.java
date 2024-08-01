package com.isl.outland_horizon.register;

import com.isl.outland_horizon.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static final RegistryObject<CreativeModeTab> TG = CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(
            "melee",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.isl.melee"))
                    .icon(() -> new ItemStack(Blocks.SAND)).withSearchBar().build());

    @SubscribeEvent
    public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
        if (tabData.getTabKey() == TG.getKey()) {
            Items.ITEM_LIST.forEach((name, item) -> tabData.accept(item.get()));
        }
    }


}
