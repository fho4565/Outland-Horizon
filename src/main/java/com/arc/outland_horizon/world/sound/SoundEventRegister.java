package com.arc.outland_horizon.world.sound;

import com.arc.outland_horizon.OutlandHorizon;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SoundEventRegister {
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OutlandHorizon.MOD_ID);
    public static final RegistryObject<SoundEvent> GUN = SOUND.register("gun", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("gun")));
    public static final RegistryObject<SoundEvent> SNIPE_GUN = SOUND.register("snipe_gun", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("snipe_gun")));
    public static final RegistryObject<SoundEvent> BOMB = SOUND.register("bomb", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("bomb")));
    public static final RegistryObject<SoundEvent> ROCKET_LAUNCHER = SOUND.register("rocket_launcher", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("rocket_launcher")));
    public static final RegistryObject<SoundEvent> MACHINE_GUN = SOUND.register("machine_gun", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("machine_gun")));
    public static final RegistryObject<SoundEvent> STAFF_SHOOT = SOUND.register("staff_shoot", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("staff_shoot")));
    public static final RegistryObject<SoundEvent> NIGHTMARE_COMES = SOUND.register("nightmare_comes", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("nightmare_comes")));
    public static final RegistryObject<SoundEvent> BA = SOUND.register("ba", () ->
            SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("ba")));
    public static final List<RegistryObject<SoundEvent>> SOUNDS = new ArrayList<>();

    public static void init() {
        SOUNDS.add(SOUND.register("nightmare_around", () ->
                SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("nightmare_around"))));
        SOUNDS.add(SOUND.register("flesh_block_breaking", () ->
                SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("flesh_block_breaking"))));
        SOUNDS.add(SOUND.register("flesh_block_broken", () ->
                SoundEvent.createVariableRangeEvent(OutlandHorizon.createModResourceLocation("flesh_block_broken"))));
        SOUNDS.add(GUN);
        SOUNDS.add(SNIPE_GUN);
        SOUNDS.add(STAFF_SHOOT);
    }
}
