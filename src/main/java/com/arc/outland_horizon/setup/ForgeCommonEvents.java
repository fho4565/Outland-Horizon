package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.ModCommands;
import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.registry.mod_effect.MobEffectRegistry;
import com.arc.outland_horizon.utils.*;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import com.arc.outland_horizon.world.entity.DamageResistance;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(OhAttributeProvider.OH_ATTRIBUTE).isPresent()) {
                event.addCapability(OutlandHorizon.createModResourceLocation("attribute"), new OhAttributeProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
        });
        player.getInventory().armor.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
        });
        player.getInventory().offhand.forEach(itemStack -> {
            if (itemStack.getItem() instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
        });
        if (event.phase == TickEvent.Phase.END) {
            if (!player.level().isClientSide) {
                CapabilityUtils.Mana.recoverMana(player);
                CapabilityUtils.Rage.recoverRage(player);
                double remove = Math.max(Math.pow((CapabilityUtils.Shield.getShieldValue(player) / 2.0 + 0.3) / 1000.0, 2), 0.005);
                CapabilityUtils.Shield.removeShieldValue(player, Math.min(remove, 0.5));
            }
        }
        if (EntityUtils.isInDimension(player, OutlandHorizon.createModResourceLocation("nightmare"))) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.NIGHTMARE_POSSESSED.get(), Utils.secondsToTicks(30)));
        }
        if (EntityUtils.isInDimension(player, new ResourceLocation("minecraft:overworld"))) {
            if (player.hasEffect(MobEffectRegistry.NIGHTMARE_POSSESSED.get()) && player instanceof ServerPlayer serverPlayer) {
                WorldUtils.playSoundForPlayer(serverPlayer, SoundEventRegister.NIGHTMARE_COMES.get(), SoundSource.PLAYERS);
                player.getActiveEffects().removeIf(effect -> effect.getEffect().equals(MobEffectRegistry.NIGHTMARE_POSSESSED.get()));
                player.addEffect(new MobEffectInstance(MobEffectRegistry.NIGHTMARE_COMES.get(), Utils.secondsToTicks(60)));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof DamageResistance resistanceEntity && event.getSource().is(DamageTypes.MAGIC)) {
            float scale = (float) ((100.0 - resistanceEntity.magicDamageResistance()) / 100.0);
            event.setAmount(Math.max(event.getAmount() * scale, 0));
        }
        if (entity.hasEffect(MobEffectRegistry.NIGHTMARE_COMES.get())) {
            event.setAmount(event.getAmount() * 2);
        }
        if (entity instanceof Player player) {
            double shieldValue = ModCapabilities.getOhAttribute(player).getShieldValue();
            float damageAmount = event.getAmount();

            float shieldAbsorb = damageAmount * 0.9f;
            float playerDamage = damageAmount * 0.1f;
            float remainingDamage = 0.0f;
            if (shieldValue >= shieldAbsorb) {
                ModCapabilities.getOhAttribute(player).setShieldValue(shieldValue - shieldAbsorb);
            } else {
                ModCapabilities.getOhAttribute(player).setShieldValue(0);
                remainingDamage = (float) (shieldAbsorb - shieldValue);
            }

            playerDamage += remainingDamage;
            event.setAmount(playerDamage);
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(MobEffectRegistry.NIGHTMARE_COMES.get())) {
            EntityUtils.hurt(entity, DamageTypes.GENERIC, 5);
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
            if (entity instanceof Player player) {
                ChatUtils.singlePlayer(player, ChatUtils.translatable("text.outland_horizon.mob_effect.nightmare_comes.sleep").withStyle(ChatFormatting.DARK_RED));
            }
        }
    }

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            event.getTrades().get(3).add(getVillagerTradeTool(6, 10, "stone_hammer", 6, 0.05f));
            event.getTrades().get(4).add(getVillagerTradeTool(8, 13, "iron_hammer", 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTradeTool(8, 13, "iron_spade", 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTradeTool(9, 13, "iron_paxel", 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTradeTool(11, 17, "iron_destroyer", 10, 0.1f));
            event.getTrades().get(5).add(getVillagerTradeTool(22, 37, "diamond_paxel", 13, 0.1f));
            event.getTrades().get(5).add(getVillagerTradeTool(29, 41, "diamond_destroyer", 18, 0.2f));
        }
    }

    private static @NotNull BasicItemListing getVillagerTradeTool(int origin, int bound, String sell, int xp, float priceMultiplier) {
        return new BasicItemListing(ThreadLocalRandom.current().nextInt(origin, bound), new ItemStack(ItemRegistry.getItemRegistered(OutlandHorizon.createModResourceLocation(sell))), 10, xp, priceMultiplier);
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
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerModCommands(event.getDispatcher());
    }

}
