package com.isl.outland_horizon.world.sound;

import com.isl.outland_horizon.utils.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SoundEventRegister {
    public static List<RegistryObject<SoundEvent>> SOUNDS = new ArrayList<>();
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Utils.MOD_ID);

    public static void init(){
        SOUNDS.add(REGISTRY.register("nightmare_around", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("nightmare_around"))));
        SOUNDS.add(REGISTRY.register("flesh_block_breaking", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_breaking"))));
        SOUNDS.add(REGISTRY.register("flesh_block_broken", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_broken"))));
    }
}
