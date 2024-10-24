package com.arc.outland_horizon.setup;

import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.registry.mod_effect.MobEffectRegistry;
import com.arc.outland_horizon.utils.*;
import com.arc.outland_horizon.world.ModCommands;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import com.arc.outland_horizon.world.entity.DamageResistance;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
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

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(OhAttributeProvider.OH_ATTRIBUTE).isPresent()) {
                event.addCapability(Utils.createModResourceLocation("attribute"), new OhAttributeProvider());
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
        if (EntityUtils.isInDimension(player, Utils.createModResourceLocation("nightmare"))) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.NIGHTMARE_POSSESSED.get(), 6000));
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity instanceof DamageResistance resistanceEntity && event.getSource().is(DamageTypes.MAGIC)){
            float scale = (float) ((100.0 - resistanceEntity.magicDamageResistance())/100.0);
            event.setAmount(Math.max(event.getAmount()*scale,0));
        }
        if (entity.hasEffect(MobEffectRegistry.NIGHTMARE_COMES.get())) {
            event.setAmount(event.getAmount() * 2);
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(MobEffectRegistry.NIGHTMARE_COMES.get())) {
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
            if (entity instanceof Player player) {
                ChatUtils.simpleMessage(player, ChatUtils.translatable("text.outland_horizon.mob_effect.nightmare_comes.sleep").withStyle(ChatFormatting.DARK_RED));
            }
        }
    }

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            event.getTrades().get(3).add(getVillagerTrade(6, 10, "stone_hammer", 10, 6, 0.05f));
            event.getTrades().get(4).add(getVillagerTrade(8, 13, "iron_hammer", 10, 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTrade(8, 13, "iron_spade", 10, 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTrade(9, 13, "iron_paxel", 10, 8, 0.06f));
            event.getTrades().get(4).add(getVillagerTrade(11, 17, "iron_destroyer", 10, 10, 0.1f));
            event.getTrades().get(5).add(getVillagerTrade(22, 37, "diamond_paxel", 10, 13, 0.1f));
            event.getTrades().get(5).add(getVillagerTrade(29, 41, "diamond_destroyer", 10, 18, 0.2f));
        }
    }

    private static @NotNull BasicItemListing getVillagerTrade(int origin, int bound, String sell, int maxTrades, int xp, float priceMultiplier) {
        return new BasicItemListing(ThreadLocalRandom.current().nextInt(origin, bound), new ItemStack(ItemRegistry.getItemRegistered(Utils.createModResourceLocation(sell))), maxTrades, xp, priceMultiplier);
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
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (event.getFrom().location().equals(Utils.createModResourceLocation("nightmare"))
                && event.getTo().location().equals(ResourceLocation.tryParse("minecraft:overworld"))) {
            if (player.hasEffect(MobEffectRegistry.NIGHTMARE_POSSESSED.get())) {
                player.getActiveEffects().removeIf(effect -> effect.getEffect().equals(MobEffectRegistry.NIGHTMARE_POSSESSED.get()));
                player.addEffect(new MobEffectInstance(MobEffectRegistry.NIGHTMARE_COMES.get(), 6000));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerModCommands(event.getDispatcher());
    }
}
