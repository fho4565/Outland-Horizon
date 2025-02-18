package com.arc.outland_horizon.network.packets;

import com.arc.outland_horizon.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class ElegyParticlePacket implements Packet {
    Entity entity;

    public ElegyParticlePacket(Entity entity) {
        this.entity = entity;
    }

    public ElegyParticlePacket(FriendlyByteBuf friendlyByteBuf) {
        if (Minecraft.getInstance().level != null) {
            EntityType.create(Objects.requireNonNull(friendlyByteBuf.readNbt()), Minecraft.getInstance().level);
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        CompoundTag compound = new CompoundTag();
        entity.save(compound);
        buf.writeNbt(compound);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        System.out.println(1);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(2);
        if (Minecraft.getInstance().level != null) {
            System.out.println(3);
            for (int i = 0; i < 25; i++) {
                System.out.println("i:" + i);
                Minecraft.getInstance().level.addParticle(ParticleTypes.ENCHANTED_HIT,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        random.nextDouble(0, 1),
                        random.nextDouble(0, 2),
                        random.nextDouble(0, 2));
            }
        }
    }
}
