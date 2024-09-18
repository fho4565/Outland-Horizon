package com.isl.outland_horizon.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.RegistryObject;

public class WorldUtils {
    public static void playSound(LevelAccessor levelAccessor, double x, double y, double z, SoundEvent soundEvent,SoundSource soundSource, float volume, float pitch) {
        if (levelAccessor instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), soundEvent, soundSource, volume, pitch);
            } else {
                level.playLocalSound(x, y, z, soundEvent, soundSource, volume, pitch, false);
            }
        }
    }
    public static void playSound(LevelAccessor levelAccessor, double x, double y, double z, SoundEvent soundEvent,SoundSource soundSource) {
        if (levelAccessor instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), soundEvent, soundSource, 1, 1);
            } else {
                level.playLocalSound(x, y, z, soundEvent, soundSource, 1, 1, false);
            }
        }
    }
    public static void summonItem(ServerPlayer player, RegistryObject<Item> item) {
        player.level().addFreshEntity(
                new ItemEntity(player.level(),
                        player.getX(), player.getY(), player.getZ(),
                        new ItemStack(item.get())));
    }
}
