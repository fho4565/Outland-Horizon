package com.arc.outland_horizon.events;

import com.arc.outland_horizon.*;
import com.arc.outland_horizon.registry.ItemRegistry;
import com.arc.outland_horizon.registry.OHItems;
import com.arc.outland_horizon.registry.OHMobEffects;
import com.arc.outland_horizon.utils.*;
import com.arc.outland_horizon.world.capability.ModCapabilities;
import com.arc.outland_horizon.world.capability.entity.OhAttribute;
import com.arc.outland_horizon.world.capability.provider.OhAttributeProvider;
import com.arc.outland_horizon.world.entity.DamageResistance;
import com.arc.outland_horizon.world.item.ICooldownItem;
import com.arc.outland_horizon.world.item.ISkillItem;
import com.arc.outland_horizon.world.item.weapons.tank.buckler.Buckler;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
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
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
            if (item instanceof ISkillItem iSkillItem) {
                iSkillItem.tickSkill(player, itemStack);
            }
        });
        player.getInventory().armor.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
            if (item instanceof ISkillItem iSkillItem) {
                iSkillItem.tickSkill(player, itemStack);
            }
        });
        player.getInventory().offhand.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                iCooldownItem.tickCooldown(player, itemStack);
            }
            if (item instanceof ISkillItem iSkillItem) {
                iSkillItem.tickSkill(player, itemStack);
            }
        });
        ArmorSuits.tick(player);
        if (!player.level().isClientSide) {
            CapabilityUtils.Mana.recoverMana(player);
            CapabilityUtils.Rage.recoverRage(player);
            double remove = Math.max(Math.pow((CapabilityUtils.Shield.getShieldValue(player) / 2.0 + 0.3) / 1000.0, 2), 0.005);
            CapabilityUtils.Shield.removeShieldValue(player, Math.min(remove, 0.5));
        }
        if (EntityUtils.isInDimension(player, OutlandHorizon.createModResourceLocation("nightmare"))) {
            player.addEffect(new MobEffectInstance(OHMobEffects.NIGHTMARE_POSSESSED.get(), Utils.secondsToTicks(30)));
        }
        if (EntityUtils.isInDimension(player, new ResourceLocation("minecraft:overworld"))) {
            if (player.hasEffect(OHMobEffects.NIGHTMARE_POSSESSED.get()) && player instanceof ServerPlayer serverPlayer) {
                WorldUtils.playSoundForPlayer(serverPlayer, SoundEventRegister.NIGHTMARE_COMES.get(), SoundSource.PLAYERS);
                player.getActiveEffects().removeIf(effect -> effect.getEffect().equals(OHMobEffects.NIGHTMARE_POSSESSED.get()));
                player.addEffect(new MobEffectInstance(OHMobEffects.NIGHTMARE_COMES.get(), Utils.secondsToTicks(60)));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.getMainHandItem().is(OHItems.Utilities.BLOOD_BEAR.get())) {
                EntityUtils.travelToDimension(serverPlayer, OutlandHorizon.createModResourceLocation("nightmare"));
                ServerLevel nextLevel = serverPlayer.server.getLevel(ResourceKey.create(Registries.DIMENSION, OutlandHorizon.createModResourceLocation("nightmare")));
                if (nextLevel != null) {
                    EntityUtils.spreadEntity(nextLevel, serverPlayer, new Vec2(0, 0), 128, 192, 128);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack useItem = entity.getUseItem();
        if (useItem.getItem() instanceof Buckler buckler && entity instanceof Player player) {
            if (!buckler.isCooldown(useItem)) {
                event.setBlockedDamage(buckler.blockDamage(player, player.getUseItem(), event.getOriginalBlockedDamage()));
                buckler.startCooldown(player, useItem);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity targetEntity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        float damageAmount = event.getAmount();
        if (targetEntity.hasEffect(OHMobEffects.NIGHTMARE_COMES.get())) {
            event.setAmount(damageAmount * 2);
        }
        if (OHDataManager.modDifficulties.getId() > ModDifficulties.DEATH.getId()) {
            double armor = targetEntity.getAttributeValue(Attributes.ARMOR);
            double armorToughness = targetEntity.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            if (damageAmount < armor) {
                damageAmount = (float) Math.max(Math.min(2.0, damageAmount * 0.1) - 2 * (100 + armorToughness) / 200.0, 0);
            } else {
                damageAmount = (float) Math.max(damageAmount - armor * (100 + armorToughness) / 200.0, 0);
            }
        }
        if (targetEntity instanceof DamageResistance resistanceEntity && event.getSource().is(DamageTypes.MAGIC)) {
            float scale = (float) ((100.0 - resistanceEntity.magicDamageResistance()) / 100.0);
            damageAmount = Math.max(damageAmount * scale, 0);
        }
        if (targetEntity instanceof ServerPlayer player) {
            if (sourceEntity instanceof Skeleton && event.getSource().is(DamageTypes.ARROW)) {
                if (Utils.chanceToTrigger(
                        switch (OHDataManager.modDifficulties) {
                            case DISABLED ->
                                    player.serverLevel().getServer().getWorldData().getDifficulty() == Difficulty.HARD ? 25 : 0;
                            case DEATH -> 50;
                            case TRIBULATION -> 75;
                            case ETERNAL -> 100;
                        })) {
                    player.addEffect(new MobEffectInstance(OHMobEffects.BLEED.get(), 100), player);
                }
            }
            double shieldValue = ModCapabilities.getOhAttribute(player).getShieldValue();
            float shieldAbsorb = damageAmount *
                    switch (OHDataManager.modDifficulties) {
                        case DISABLED -> 0.5f;
                        case DEATH -> 0.7f;
                        case TRIBULATION -> 0.85f;
                        case ETERNAL -> 1;
                    };
            float playerDamage = damageAmount - shieldAbsorb;
            if (shieldValue < shieldAbsorb) {
                playerDamage += (float) (shieldAbsorb - shieldValue);
                ModCapabilities.getOhAttribute(player).setShieldValue(0);
            } else {
                ModCapabilities.getOhAttribute(player).setShieldValue(shieldValue - shieldAbsorb);
            }
            event.setAmount(playerDamage);
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(OHMobEffects.NIGHTMARE_COMES.get())) {
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
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (OHDataManager.modDifficulties == ModDifficulties.ETERNAL) {
            if (entity instanceof Zombie zombie) {
                AttributeInstance attribute = zombie.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null) {
                    float scale = zombie.getHealth() / zombie.getMaxHealth();
                    UUID uuid = Utils.generateUUIDFromText("outland_horizon.zombie.speed_up");
                    AttributeModifier attributeModifier = new AttributeModifier(uuid, "outland_horizon.zombie.speed_up", (1 - scale) * 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
                    attribute.removeModifier(uuid);
                    attribute.addTransientModifier(attributeModifier);
                }
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
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerModCommands(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (event.getEntity() instanceof Player && !effectInstance.getEffect().isBeneficial()) {
            switch (OHDataManager.modDifficulties) {
                case DEATH -> effectInstance.duration *= 2;
                case TRIBULATION -> {
                    effectInstance.duration *= 2;
                    effectInstance.amplifier += 1;
                }
                case ETERNAL -> {
                    effectInstance.duration *= 3;
                    effectInstance.amplifier += 2;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Enemy && entity instanceof Mob mob) {
            ModDifficulties.applyDifficultySettingsForEntity(mob);
            mob.heal(2147483647);
        }
    }
}
