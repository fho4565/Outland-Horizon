package com.arc.outland_horizon.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public static String getWorldFolderPath(MinecraftServer server){
        return server.getWorldPath(new LevelResource("")).toAbsolutePath().toString().replace("\\.\\","\\");
    }
    public static List<BlockState> getBlocksByRadio(Level level, Vec3 center, double radius) {
        AABB box = new AABB(center.x - radius - 1, center.y - radius - 1, center.z - radius - 1,
                center.x + radius + 1, center.y + radius + 1, center.z + radius + 1);
        return BlockPos.betweenClosedStream(box).filter(blockPos -> center.distanceTo(blockPos.getCenter())<=radius)
                .map(level::getBlockState).collect(Collectors.toCollection(List::of));
    }
    public static List<BlockState> getBlocksByRadio(Level level, Vec3 center, double radius, Predicate<BlockState> blockStatePredicate) {
        AABB box = new AABB(center.x - radius - 1, center.y - radius - 1, center.z - radius - 1,
                center.x + radius + 1, center.y + radius + 1, center.z + radius + 1);
        return BlockPos.betweenClosedStream(box)
                .filter(blockPos -> center.distanceTo(blockPos.getCenter())<=radius&&blockStatePredicate.test(level.getBlockState(blockPos)))
                .map(level::getBlockState).toList();
    }
}
