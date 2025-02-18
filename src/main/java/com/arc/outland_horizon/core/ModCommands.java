package com.arc.outland_horizon.core;

import com.arc.outland_horizon.client.CustomToast;
import com.arc.outland_horizon.develop.ModLang;
import com.arc.outland_horizon.events.DevelopEvents;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.item.ISkillItem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

public class ModCommands {
    public static void registerModCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rage").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current rage：").append(String.valueOf(CapabilityUtils.Rage.getRage(serverPlayer))).append("\n")
                    .append(Component.literal("max rage：").append(String.valueOf(CapabilityUtils.Rage.getMaxRage(serverPlayer))));
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        }));
        dispatcher.register(Commands.literal("mana").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current mana：").append(String.valueOf(CapabilityUtils.Mana.getMana(serverPlayer))).append("\n")
                    .append(Component.literal("max mana：").append(String.valueOf(CapabilityUtils.Mana.getMaxMana(serverPlayer))));
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
        dispatcher.register(Commands.literal("shield").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current shield value：").append(String.valueOf(CapabilityUtils.Shield.getShieldValue(serverPlayer)));
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        }).then(Commands.argument("amount", DoubleArgumentType.doubleArg(0)).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            CapabilityUtils.Shield.setShieldValue(serverPlayer, DoubleArgumentType.getDouble(ctx, "amount"));
            Component msg = Component.literal("set shield value");
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        })));
        dispatcher.register(Commands.literal("sp").requires(requireDevelopEnvironment()).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            Component msg = Component.literal("current sp value：").append(String.valueOf(CapabilityUtils.Sp.getSp(serverPlayer)));
            ChatUtils.singlePlayer(serverPlayer, msg);
            return 0;
        }).then(Commands.argument("amount", DoubleArgumentType.doubleArg(0)).executes(ctx -> {
            ServerPlayer serverPlayer = ctx.getSource().getPlayerOrException();
            CapabilityUtils.Sp.setSp(serverPlayer, DoubleArgumentType.getDouble(ctx, "amount"));
            ChatUtils.singlePlayer(serverPlayer, Component.literal("set sp value"));
            return 0;
        })));
        dispatcher.register(Commands.literal("modData").requires(requireDevelopEnvironment()).executes(ctx -> {
            ctx.getSource().sendSuccess(() -> {
                try {
                    return Component.literal("整理出" + ModLang.generate() + "条待处理翻译键");
                } catch (IOException e) {
                    Utils.LOGGER.error(e.getLocalizedMessage());
                }
                return Component.literal("翻译键整理失败");
            }, true);
            return 1;
        }));
        dispatcher.register(Commands.literal("modDebug").requires(requireDevelopEnvironment()).executes(ctx -> {
            DevelopEvents.isDebug = !DevelopEvents.isDebug;
            ctx.getSource().sendSuccess(() -> Component.literal("调试状态：" + DevelopEvents.isDebug), true);
            return 1;
        }));
        dispatcher.register(Commands.literal("modItem").executes(ctx -> {
            Player player = ctx.getSource().getPlayer();
            if (player != null) {
                ItemStack itemStack = player.getMainHandItem();
                ChatUtils.singlePlayer(player, Component.literal(">>>DEBUG INFO"));

                if (itemStack.getItem() instanceof ISkillItem skillItem) {
                    ChatUtils.singlePlayer(player, Component.literal("current skill name：").append(skillItem.currentSkill(itemStack).name()));
                    ChatUtils.singlePlayer(player, Component.literal("current skill description：").append(skillItem.currentSkill(itemStack).description()));
                    ChatUtils.singlePlayer(player, Component.literal("current skill tag：").append(skillItem.currentSkillTag(itemStack).getAsString()));
                }
                if (itemStack.getItem() instanceof ICooldownItem cooldownItem) {
                    ChatUtils.singlePlayer(player, Component.literal("current cooldown：").append(String.valueOf(cooldownItem.getCurrentCooldown(itemStack))));
                    ChatUtils.singlePlayer(player, Component.literal("max cooldown：").append(String.valueOf(cooldownItem.cooldownTime())));

                }

            }
            return 1;
        }));
        dispatcher.register(Commands.literal("debugger").executes(context -> {
            ServerPlayer player = context.getSource().getPlayer();
            WorldUtils.toast(player, CustomToast.Builder.of().build());
            return 1;
        }));
        dispatcher.register(Commands.literal("loadedMods").executes(context -> {
            FMLLoader.getLoadingModList().getModFiles().forEach(modFileInfo -> {
                System.out.println("------");
                modFileInfo.getMods().forEach(iModInfo -> System.out.println(iModInfo.getModId()));
                System.out.println("------");
            });
            return 1;
        }));
    }

    private static @NotNull Predicate<CommandSourceStack> requireDevelopEnvironment() {
        return source -> Utils.isDevelopEnvironment();
    }
}
