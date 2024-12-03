package com.arc.outland_horizon.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.function.Function;

public class EntityUtils {
    public static void hurt(Entity source, Entity target, ResourceKey<DamageType> damageType, float damage) {
        target.hurt(new DamageSource(source.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), source), damage);
    }

    public static void hurt(Entity target, ResourceKey<DamageType> damageType, float damage) {
        target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType)), damage);
    }

    public static boolean isInDimension(Entity entity, ResourceLocation dimensionLocation) {
        return entity.level().dimension().location().compareTo(dimensionLocation) == 0;
    }

    public static void travelToDimension(ServerPlayer serverPlayer, ServerLevel serverLevel, Vec3 pos) {
        float f = Mth.wrapDegrees(serverPlayer.getYRot());
        float f1 = Mth.wrapDegrees(serverPlayer.getXRot());
        serverPlayer.teleportTo(serverLevel, pos.x(), pos.y(), pos.z, EnumSet.noneOf(RelativeMovement.class), f, f1);
    }

    public static @NotNull ResourceKey<DamageType> getMachineGun(LivingEntity holder, ResourceLocation location) {
        return holder.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, location)).key();
    }

    public static boolean isMoving(LivingEntity entity) {
        return entity.getDeltaMovement().lengthSqr() > 0.015;
    }

    public static boolean isAttacking(LivingEntity entity) {
        return entity.getAttackAnim(1.0F) > 0.0F;
    }

    public static void teleportToDimension(ServerLevel serverLevel, Entity entity, double x, double y, double z) {
        entity.changeDimension(serverLevel, new ITeleporter() {
            @Override
            public @NotNull Entity placeEntity(@NotNull Entity entity, @NotNull ServerLevel currentWorld, @NotNull ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPos(x, y, z);
                return entity;
            }
        });
    }

    public static void spreadEntity(ServerLevel serverlevel, Entity entity, Vec2 center, float spreadDistance, float maxDistance, int maxHeight) {
        int minBuildHeight = serverlevel.getMinBuildHeight();
        if (maxHeight >= minBuildHeight) {
            RandomSource randomsource = RandomSource.create();
            double pMinX = center.x - maxDistance;
            double pMinZ = center.y - maxDistance;
            double pMaxX = center.x + maxDistance;
            double pMaxZ = center.y + maxDistance;
            EntityUtils.Position position = new EntityUtils.Position();
            position.randomize(randomsource, pMinX, pMinZ, pMaxX, pMaxZ);
            boolean flag = true;
            for (int i = 0; i < 10000 && flag; ++i) {
                flag = false;
                EntityUtils.Position pos = new EntityUtils.Position();
                double d1 = position.dist(position);
                int k = 0;
                if (d1 < spreadDistance) {
                    ++k;
                }
                if (k > 0) {
                    pos.x /= k;
                    pos.z /= k;
                    if (pos.getLength() > 0.0D) {
                        pos.normalize();
                        position.moveAway(pos);
                    } else {
                        position.randomize(randomsource, pMinX, pMinZ, pMaxX, pMaxZ);
                    }
                    flag = true;
                }

                if (position.clamp(pMinX, pMinZ, pMaxX, pMaxZ)) {
                    flag = true;
                }


                if (!flag) {
                    if (!position.isSafe(serverlevel, maxHeight)) {
                        position.randomize(randomsource, pMinX, pMinZ, pMaxX, pMaxZ);
                        flag = true;
                    }
                }
            }
            net.minecraftforge.event.entity.EntityTeleportEvent.SpreadPlayersCommand event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportSpreadPlayersCommand(entity, (double) Mth.floor(position.x) + 0.5D, position.getSpawnY(serverlevel, maxHeight), (double) Mth.floor(position.z) + 0.5D);
            if (!event.isCanceled()) {
                entity.teleportToWithTicket(event.getTargetX(), event.getTargetY(), event.getTargetZ());
            }
        }
    }

    public static Vec3 getRandomSpreadPosition(ServerLevel serverLevel, Vec2 center, float maxDistance, int maxHeight) {
        double minX = center.x - maxDistance;
        double minZ = center.y - maxDistance;
        double maxX = center.x + maxDistance;
        double maxZ = center.y + maxDistance;
        EntityUtils.Position position = new EntityUtils.Position();
        RandomSource randomSource = RandomSource.create();
        position.randomize(randomSource, minX, minZ, maxX, maxZ);
        boolean flag = true;
        for (int i = 0; i < 10000 && flag; ++i) {
            flag = false;
            EntityUtils.Position pos = new EntityUtils.Position();
            double distance = position.dist(position);
            int k = 0;
            if (distance < maxDistance) {
                ++k;
            }
            if (k > 0) {
                pos.x /= k;
                pos.z /= k;
                if (pos.getLength() > 0.0D) {
                    pos.normalize();
                    position.moveAway(pos);
                } else {
                    position.randomize(randomSource, minX, minZ, maxX, maxZ);
                }
                flag = true;
            }
            if (position.clamp(minX, minZ, maxX, maxZ)) {
                flag = true;
            }
            if (!flag) {
                if (!position.isSafe(serverLevel, maxHeight)) {
                    position.randomize(randomSource, minX, minZ, maxX, maxZ);
                    flag = true;
                }
            }
        }
        return new Vec3((double) Mth.floor(position.x) + 0.5D, position.getSpawnY(serverLevel, maxHeight), (double) Mth.floor(position.z) + 0.5D);
    }

    static class Position {
        double x;
        double z;

        double dist(EntityUtils.Position pOther) {
            double d0 = this.x - pOther.x;
            double d1 = this.z - pOther.z;
            return Math.sqrt(d0 * d0 + d1 * d1);
        }

        void normalize() {
            double d0 = this.getLength();
            this.x /= d0;
            this.z /= d0;
        }

        double getLength() {
            return Math.sqrt(this.x * this.x + this.z * this.z);
        }

        public void moveAway(EntityUtils.Position pOther) {
            this.x -= pOther.x;
            this.z -= pOther.z;
        }

        public boolean clamp(double pMinX, double pMinZ, double pMaxX, double pMaxZ) {
            boolean flag = false;
            if (this.x < pMinX) {
                this.x = pMinX;
                flag = true;
            } else if (this.x > pMaxX) {
                this.x = pMaxX;
                flag = true;
            }

            if (this.z < pMinZ) {
                this.z = pMinZ;
                flag = true;
            } else if (this.z > pMaxZ) {
                this.z = pMaxZ;
                flag = true;
            }

            return flag;
        }

        public int getSpawnY(BlockGetter pLevel, int pY) {
            BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(this.x, pY + 1, this.z);
            boolean flag = pLevel.getBlockState(blockPos).isAir();
            blockPos.move(Direction.DOWN);
            boolean flag2;
            for (boolean flag1 = pLevel.getBlockState(blockPos).isAir(); blockPos.getY() > pLevel.getMinBuildHeight(); flag1 = flag2) {
                blockPos.move(Direction.DOWN);
                flag2 = pLevel.getBlockState(blockPos).isAir();
                if (!flag2 && flag1 && flag) {
                    return blockPos.getY() + 1;
                }
                flag = flag1;
            }

            return pY + 1;
        }

        public boolean isSafe(BlockGetter pLevel, int pY) {
            BlockPos blockpos = BlockPos.containing(this.x, this.getSpawnY(pLevel, pY) - 1, this.z);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            return blockpos.getY() < pY && !blockstate.liquid() && !blockstate.is(BlockTags.FIRE);
        }

        public void randomize(RandomSource pRandom, double pMinX, double pMinZ, double pMaxX, double pMaxZ) {
            this.x = Mth.nextDouble(pRandom, pMinX, pMaxX);
            this.z = Mth.nextDouble(pRandom, pMinZ, pMaxZ);
        }
    }
}
