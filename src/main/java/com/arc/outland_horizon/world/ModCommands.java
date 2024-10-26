package com.arc.outland_horizon.world;

import com.arc.outland_horizon.develop.ModLang;
import com.arc.outland_horizon.develop.ModLootTable;
import com.arc.outland_horizon.develop.ModRecipes;
import com.arc.outland_horizon.registry.block.Natural;
import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.ModCapabilityUtils;
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
            Component msg = Component.literal("current rage：").append(String.valueOf(ModCapabilityUtils.getRage(serverPlayer))).append("\n")
                    .append(Component.literal("max rage：").append(String.valueOf(ModCapabilityUtils.getMaxRage(serverPlayer))));
            ChatUtils.simpleMessage(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("mana").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current mana：").append(String.valueOf(ModCapabilityUtils.getMana(serverPlayer))).append("\n")
                    .append(Component.literal("max mana：").append(String.valueOf(ModCapabilityUtils.getMaxMana(serverPlayer))));
            ChatUtils.simpleMessage(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("attributes").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Objects.requireNonNull(serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE)).getModifiers().forEach(attributeModifier -> {
                ChatUtils.simpleMessage(serverPlayer, attributeModifier.getName());
                ChatUtils.simpleMessage(serverPlayer, String.valueOf(attributeModifier.getId()));
            });
            return 0;
        }));
        dispatcher.register(Commands.literal("gendata").requires(requireDevelopEnvironment())
                .executes(ctx -> {
                    try {
                        ChatUtils.simpleMessage(ctx.getSource().getPlayerOrException(), "整理出" + ModLang.generate(ctx.getSource().getServer()) + "条待翻译条目");
                        ChatUtils.simpleMessage(ctx.getSource().getPlayerOrException(), "生成了" + ModRecipes.recipes(ctx.getSource().getServer()) + "个配方");
                        Natural.genData();
                        ChatUtils.simpleMessage(ctx.getSource().getPlayerOrException(), "生成了" + (ModLootTable.of().lootTables(ctx.getSource().getServer())) + "个战利品表");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return 0;
                })
        );
        dispatcher.register(Commands.literal("shield").requires(requireDevelopEnvironment())
                .executes(ctx -> {
                    ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
                    Component msg = Component.literal("current shield value：").append(String.valueOf(ModCapabilityUtils.getShieldValue(serverPlayer)));
                    ChatUtils.simpleMessage(serverPlayer, msg);
                    return 0;
                })
                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                        .executes(ctx -> {
                            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
                            ModCapabilityUtils.setShieldValue(serverPlayer, DoubleArgumentType.getDouble(ctx, "amount"));
                            Component msg = Component.literal("set shield value");
                            ChatUtils.simpleMessage(serverPlayer, msg);
                            return 0;
                        })
                )
        );

    }

    private static @NotNull Predicate<CommandSourceStack> requireDevelopEnvironment() {
        return source -> Utils.isDevelopEnvironment();
    }
}
