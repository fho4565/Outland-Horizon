package com.arc.outland_horizon;

import com.arc.outland_horizon.config.Configs;
import com.arc.outland_horizon.registry.block.Building;
import com.arc.outland_horizon.registry.block.Fluid;
import com.arc.outland_horizon.registry.block.Functional;
import com.arc.outland_horizon.registry.block.Natural;
import com.arc.outland_horizon.registry.item.*;
import com.arc.outland_horizon.registry.item.weapons.Magic;
import com.arc.outland_horizon.registry.item.weapons.Melee;
import com.arc.outland_horizon.registry.item.weapons.Ranged;
import com.arc.outland_horizon.registry.mod_effect.MobEffectRegistry;
import com.arc.outland_horizon.utils.MaterialPack;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.ModArmorMaterials;
import com.arc.outland_horizon.world.ModTiers;
import com.arc.outland_horizon.registry.block.BlockRegistry;
import com.arc.outland_horizon.world.entity.EntityRegistry;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
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

        MaterialPack.create(MaterialPack.MaterialType.GEM,"blue", ModTiers.BLUE_GEM,ModArmorMaterials.BLUE_GEM, 2,false);
        MaterialPack.create(MaterialPack.MaterialType.CUSTOM,"blood_stone",ModTiers.BLOOD_STONE, ModArmorMaterials.BLOOD_STONE, 3,false);
        initAll();
        ItemRegistry.register(bus);
        BlockRegistry.register(bus);
        Fluid.FluidRegistry.FLUIDS.register(bus);
        Fluid.FluidTypeRegistry.FLUID_TYPES.register(bus);
        MobEffectRegistry.register(bus);
        EntityRegistry.EntityRenders.init();
        EntityRegistry.register(bus);
        SoundEventRegister.init();
        SoundEventRegister.REGISTRY.register(bus);
    }
    private static void initAll() {
        Magic.init();
        Melee.init();
        Ranged.init();
        Armors.init();
        BlockItems.init();
        Items.init();
        Consumables.init();
        Materials.init();
        Tools.init();
        Fluid.init();
        Building.init();
        Medal.init();
        Functional.init();
        Natural.init();
    }

}
