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
    public static final RegistryObject<SoundEvent> GUN = REGISTRY.register("gun", () ->
            SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("gun")));
    public static final RegistryObject<SoundEvent> SNIPE_GUN = REGISTRY.register("snipe_gun", () ->
            SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("snipe_gun")));
    public static final RegistryObject<SoundEvent> BOMB = REGISTRY.register("bomb", () ->
            SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("bomb")));
    public static final RegistryObject<SoundEvent> ROCKET_LAUNCHER = REGISTRY.register("rocket_launcher", () ->
            SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("rocket_launcher")));

    public static void init(){
        SOUNDS.add(REGISTRY.register("nightmare_around", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("nightmare_around"))));
        SOUNDS.add(REGISTRY.register("flesh_block_breaking", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_breaking"))));
        SOUNDS.add(REGISTRY.register("flesh_block_broken", () ->
                SoundEvent.createVariableRangeEvent(Utils.createResourceLocation("flesh_block_broken"))));
        SOUNDS.add(GUN);
        SOUNDS.add(SNIPE_GUN);
    }
}
