package com.arc.outland_horizon.world.sound;

import com.arc.outland_horizon.utils.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SoundEventRegister {
    public static List<RegistryObject<SoundEvent>> SOUNDS = new ArrayList<>();
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Utils.MOD_ID);
    public static final RegistryObject<SoundEvent> GUN = SOUND.register("gun", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("gun")));
    public static final RegistryObject<SoundEvent> SNIPE_GUN = SOUND.register("snipe_gun", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("snipe_gun")));
    public static final RegistryObject<SoundEvent> BOMB = SOUND.register("bomb", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("bomb")));
    public static final RegistryObject<SoundEvent> ROCKET_LAUNCHER = SOUND.register("rocket_launcher", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("rocket_launcher")));
    public static final RegistryObject<SoundEvent> MACHINE_GUN = SOUND.register("machine_gun", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("machine_gun")));
    public static final RegistryObject<SoundEvent> STAFF_SHOOT = SOUND.register("staff_shoot", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("staff_shoot")));
    public static final RegistryObject<SoundEvent> NIGHTMARE_COMES = SOUND.register("nightmare_comes", () ->
            SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("nightmare_comes")));

    public static void init(){
        SOUNDS.add(SOUND.register("nightmare_around", () ->
                SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("nightmare_around"))));
        SOUNDS.add(SOUND.register("flesh_block_breaking", () ->
                SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("flesh_block_breaking"))));
        SOUNDS.add(SOUND.register("flesh_block_broken", () ->
                SoundEvent.createVariableRangeEvent(Utils.createModResourceLocation("flesh_block_broken"))));
        SOUNDS.add(GUN);
        SOUNDS.add(SNIPE_GUN);
        SOUNDS.add(STAFF_SHOOT);
    }
}
