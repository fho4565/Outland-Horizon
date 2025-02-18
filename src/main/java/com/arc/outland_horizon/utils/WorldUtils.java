package com.arc.outland_horizon.utils;

import com.arc.outland_horizon.client.CustomToast;
import com.arc.outland_horizon.network.NetworkHandler;
import com.arc.outland_horizon.network.packets.ToastPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WorldUtils {
    public static void playSound(LevelAccessor levelAccessor, double x, double y, double z, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch) {
        if (levelAccessor instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), soundEvent, soundSource, volume, pitch);
            } else {
                level.playLocalSound(x, y, z, soundEvent, soundSource, volume, pitch, false);
            }
        }
    }

    public static void playSound(LevelAccessor levelAccessor, double x, double y, double z, SoundEvent soundEvent, SoundSource soundSource) {
        if (levelAccessor instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), soundEvent, soundSource, 1, 1);
            } else {
                level.playLocalSound(x, y, z, soundEvent, soundSource, 1, 1, false);
            }
        }
    }

    public static void playSoundForPlayer(ServerPlayer serverPlayer, SoundEvent soundEvent, SoundSource soundSource) {
        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(soundEvent),
                soundSource,
                serverPlayer.getX(),
                serverPlayer.getY(),
                serverPlayer.getZ(),
                1,
                1,
                serverPlayer.level().getRandom().nextLong()));

    }

    public static void toast(Player player, CustomToast customToast) {
        NetworkHandler.sendToPlayer(new ToastPacket(customToast.save()), player);
    }

    public static void summonItem(ServerPlayer player, RegistryObject<Item> item) {
        player.level().addFreshEntity(
                new ItemEntity(player.level(),
                        player.getX(), player.getY(), player.getZ(),
                        new ItemStack(item.get())));
    }

    public static LinkedHashSet<BlockPos> getBlocksByRadio(Level level, BlockPos center, int radius) {
        return getAllBlocksByRadio(level, center, radius, block -> true);
    }

    /**
     * 获取指定球形范围内的所有满足条件的方块坐标，坐标会按照离中心距离从小到大排序
     *
     * @param level               Level对象
     * @param center              中心点的坐标
     * @param radius              球形范围半径
     * @param blockStatePredicate 方块测试
     * @return LinkedHashSet对象，保存了满足条件的方块的坐标
     */
    public static LinkedHashSet<BlockPos> getAllBlocksByRadio(Level level, BlockPos center, int radius, Predicate<BlockState> blockStatePredicate) {
        AABB box = new AABB(center.getX() - radius, center.getY() - radius, center.getZ() - radius,
                center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        return BlockPos.betweenClosedStream(box)
                .filter(pos -> center.getCenter().distanceTo(pos.getCenter()) <= radius && blockStatePredicate.test(level.getBlockState(pos)))
                .map(BlockPos::immutable)
                .sorted(Comparator.comparingDouble(pos -> pos.getCenter().distanceToSqr(center.getCenter())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 获取指定球形范围内的所有满足条件而且和中心坐标直接或者间接相连(不包括对角线)的的方块坐标，坐标会按照离中心距离从小到大排序
     *
     * @param level               Level对象
     * @param center              中心点的坐标
     * @param radius              球形范围半径
     * @param blockStatePredicate 方块测试
     * @return LinkedHashSet对象，保存了满足条件的方块的坐标
     */
    public static LinkedHashSet<BlockPos> getConnectedBlocksByRadio(Level level, BlockPos center, int radius, Predicate<BlockState> blockStatePredicate) {
        LinkedHashSet<BlockPos> connectedBlocks = new LinkedHashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        HashSet<BlockPos> visited = new HashSet<>();

        queue.offer(center);
        visited.add(center);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                BlockPos current = queue.poll();
                if (current != null) {
                    connectedBlocks.add(current);
                    for (BlockPos neighbor : Arrays.stream(Direction.values())
                            .map(current::relative)
                            .filter(blockPos -> blockStatePredicate.test(level.getBlockState(current)))
                            .toList()) {
                        if (!visited.contains(neighbor) && (neighbor.distSqr(center) <= radius * radius)) {
                            queue.offer(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
            }
        }
        return connectedBlocks;
    }

    public static boolean hasBlockInRadius(Level level, BlockPos center, int radius, Block block) {
        return getBlocksByRadio(level, center, radius).stream().anyMatch(blockPos -> level.getBlockState(blockPos).is(block));
    }

    public static List<Entity> getEntitiesByRadio(Level level, Vec3 pos, double radius) {
        AABB box = new AABB(pos.x - radius - 1, pos.y - radius - 1, pos.z - radius - 1,
                pos.x + radius + 1, pos.y + radius + 1, pos.z + radius + 1);
        return level.getEntities(null, box).stream()
                .filter(entity -> entity.position().distanceTo(pos) <= radius)
                .toList();
    }

    public static List<Entity> getEntitiesByRadio(Level level, Vec3 pos, double radius, Predicate<Entity> entityPredicate) {
        return getEntitiesByRadio(level, pos, radius).stream()
                .filter(entityPredicate)
                .toList();
    }

}
