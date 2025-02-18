package com.arc.outland_horizon.network.packets;

import com.arc.outland_horizon.network.Packet;
import com.arc.outland_horizon.registry.OHMobEffects;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import com.arc.outland_horizon.world.item.ISkillItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class C2SPacket implements Packet {
    private final int operation;

    public C2SPacket(int operation) {
        this.operation = operation;
    }

    public C2SPacket(FriendlyByteBuf buf) {
        this.operation = buf.readByte();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.operation);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer serverPlayer = Objects.requireNonNull(ctx.get().getSender());
        switch (this.operation) {
            case Operation.TRIGGER_RAGE -> {
                if (CapabilityUtils.Rage.isRageFull(serverPlayer)) {
                    serverPlayer.addEffect(new MobEffectInstance(OHMobEffects.RAGE.get(), OhAttributeProvider.madTime, OhAttributeProvider.madDamageBonus));
                    CapabilityUtils.Rage.setRage(serverPlayer, 0);
                }
            }
            case Operation.TRIGGER_SKILL -> {
                ItemStack itemStack = serverPlayer.getMainHandItem();
                if (itemStack.getItem() instanceof ISkillItem skillItem) {
                    skillItem.triggerSkill(serverPlayer, itemStack);
                }
            }
            case Operation.SWITCH_SKILL -> {
                ItemStack itemStack = serverPlayer.getMainHandItem();
                if (itemStack.getItem() instanceof ISkillItem skillItem) {
                    skillItem.switchSkill(itemStack);
                }
            }
        }
        ctx.get().setPacketHandled(true);
    }

    public static class Operation {
        public static final int TRIGGER_RAGE = 0;
        public static final int TRIGGER_SKILL = 1;
        public static final int SWITCH_SKILL = 2;
    }
}
