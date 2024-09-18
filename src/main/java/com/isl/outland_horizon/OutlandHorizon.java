package com.isl.outland_horizon;

import com.isl.outland_horizon.config.Configs;
import com.isl.outland_horizon.utils.MaterialPack;
import com.isl.outland_horizon.utils.ToolPack;
import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.ModArmorMaterials;
import com.isl.outland_horizon.world.ModTiers;
import com.isl.outland_horizon.world.block.*;
import com.isl.outland_horizon.world.entity.EntityRegistry;
import com.isl.outland_horizon.world.item.ItemRegistry;
import com.isl.outland_horizon.world.item.registry.*;
import com.isl.outland_horizon.world.item.registry.weapons.Magic;
import com.isl.outland_horizon.world.item.registry.weapons.Melee;
import com.isl.outland_horizon.world.item.registry.weapons.Ranged;
import com.isl.outland_horizon.world.mod_effect.EffectRegistry;
import com.isl.outland_horizon.world.sound.SoundEventRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class OutlandHorizon {
    public static IEventBus bus;
    public OutlandHorizon() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configs.COMMON_CONFIG);

        ToolPack.create(ToolPack.MaterialType.GEM,"blue", ModTiers.BLUE_GEM, 2);
        MaterialPack.create(MaterialPack.MaterialType.CUSTOM,"blood_stone",ModTiers.BLOOD_STONE, ModArmorMaterials.BLOOD_STONE, 3);
        Magic.init();
        Melee.init();
        Ranged.init();
        Armors.init();
        BlockItems.init();
        Consumables.init();
        Materials.init();
        Tools.init();
        Fluid.init();
        Building.init();
        Functional.init();
        Natural.init();
        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        Fluid.FluidRegistry.FLUIDS.register(bus);
        Fluid.FluidTypeRegistry.FLUID_TYPES.register(bus);
        EffectRegistry.register(bus);
        EntityRegistry.EntityRenders.init();
        EntityRegistry.register(bus);
        SoundEventRegister.init();
        SoundEventRegister.REGISTRY.register(bus);
    }

}
