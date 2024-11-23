package com.arc.outland_horizon;

import com.arc.outland_horizon.develop.ModLang;
import com.arc.outland_horizon.setup.DevelopEvents;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

public class ModCommands {
    public static void registerModCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rage").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current rage：").append(String.valueOf(CapabilityUtils.getRage(serverPlayer))).append("\n")
                    .append(Component.literal("max rage：").append(String.valueOf(CapabilityUtils.getMaxRage(serverPlayer))));
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("mana").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current mana：").append(String.valueOf(CapabilityUtils.getMana(serverPlayer))).append("\n")
                    .append(Component.literal("max mana：").append(String.valueOf(CapabilityUtils.getMaxMana(serverPlayer))));
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("attributes").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE)).getModifiers().forEach(attributeModifier -> {
                ChatUtils.singlePlayer(serverPlayer, attributeModifier.getName());
                ChatUtils.singlePlayer(serverPlayer, String.valueOf(attributeModifier.getId()));
            });
            return 0;
        }));
        dispatcher.register(Commands.literal("shield").requires(requireDevelopEnvironment())
                .executes(ctx -> {
                    ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
                    Component msg = Component.literal("current shield value：").append(String.valueOf(CapabilityUtils.getShieldValue(serverPlayer)));
                    ChatUtils.singlePlayer(serverPlayer, msg);
                    return 0;
                })
                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                        .executes(ctx -> {
                            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
                            CapabilityUtils.setShieldValue(serverPlayer, DoubleArgumentType.getDouble(ctx, "amount"));
                            Component msg = Component.literal("set shield value");
                            ChatUtils.singlePlayer(serverPlayer, msg);
                            return 0;
                        })
                )
        );
        dispatcher.register(Commands.literal("modData").requires(requireDevelopEnvironment())
                .executes(ctx -> {
                    ctx.getSource().sendSuccess(() -> {
                        try {
                            return Component.literal("整理出" + ModLang.generate() + "条待处理翻译键");
                        } catch (IOException e) {
                            Utils.LOGGER.error(e.getLocalizedMessage());
                        }
                        return Component.literal("翻译键整理失败");
                    }, true);
                    return 1;
                })
        );
        dispatcher.register(Commands.literal("modDebug").requires(requireDevelopEnvironment())
                .executes(ctx -> {
                    DevelopEvents.isDebug = !DevelopEvents.isDebug;
                    System.out.println("调试状态：" + DevelopEvents.isDebug);
                    return 1;
                })
        );

    }

    private static @NotNull Predicate<CommandSourceStack> requireDevelopEnvironment() {
        return source -> Utils.isDevelopEnvironment();
    }
}
