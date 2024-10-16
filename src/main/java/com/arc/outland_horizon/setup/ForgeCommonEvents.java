package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.utils.*;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player){
            if(!player.getCapability(OhAttributeProvider.OH_ATTRIBUTE).isPresent()) {
                event.addCapability(Utils.createResourceLocation("attribute"), new OhAttributeProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (!player.level().isClientSide) {
                ManaUtils.recoverMana(player);
                RageUtils.recoverRage(player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        LazyOptional<OhAttribute> oldCap = event.getOriginal().getCapability(ModCapabilities.OH_ATTRIBUTE);
        LazyOptional<OhAttribute> newCap = event.getEntity().getCapability(ModCapabilities.OH_ATTRIBUTE);
        if (oldCap.isPresent() && newCap.isPresent()) {
            newCap.ifPresent((newCap1) -> oldCap.ifPresent((oldCap1) -> newCap1.deserializeNBT(oldCap1.serializeNBT())));
        }
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onServerStaring(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("rage").executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayer();
            Component msg = Component.literal("current rage：").append(String.valueOf(RageUtils.getRage(serverPlayer))).append("\n")
                    .append(Component.literal("max rage：").append(String.valueOf(RageUtils.getMaxRage(serverPlayer))));
            ChatUtils.sendSimpleMessageToPlayer(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("mana").executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayer();
            Component msg = Component.literal("current mana：").append(String.valueOf(ManaUtils.getMana(serverPlayer))).append("\n")
                            .append(Component.literal("max mana：").append(String.valueOf(ManaUtils.getMaxMana(serverPlayer))));
            ChatUtils.sendSimpleMessageToPlayer(serverPlayer, msg);
            return 0;
        }));
    }

}
