package com.arc.outland_horizon.registry;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class OHStructures {


    public static final ResourceKey<Structure> HEDGE_MAZE = registerKey("blood_crazier");

    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, OutlandHorizon.createModResourceLocation(name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        //context.register(HEDGE_MAZE,new );
    }
}
