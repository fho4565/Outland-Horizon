package com.arc.outland_horizon.events;

import com.arc.outland_horizon.*;
import com.arc.outland_horizon.registry.ItemRegistry;
import com.arc.outland_horizon.registry.OHDimensions;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Mod.EventBusSubscriber(modid = OutlandHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    public static final UUID ZOMBIE_SPEED_UP = UUID.fromString("132aebd7-ee58-3ffd-989a-b3cfa3099c82");
    private static final String DATA_INIT = "INIT";

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        OHItems.initCurio();
    }

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
        CuriosApi.getCuriosInventory(player).resolve().ifPresent(iCuriosItemHandler -> iCuriosItemHandler.getCurios().forEach((s, iCurioStacksHandler) -> {
            ItemStack itemStack = iCurioStacksHandler.getStacks().getStackInSlot(0);
            if ((itemStack.getItem() instanceof ICooldownItem iCooldownItem) && itemStack.getItem() instanceof ICurioItem) {
                if (iCooldownItem.shouldTick(itemStack)) {
                    iCooldownItem.tickCooldown(player, itemStack);
                }
            }
        }));
        player.getInventory().items.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                if (!(item instanceof ICurioItem)) {
                    iCooldownItem.tickCooldown(player, itemStack);
                }
            }
            if (item instanceof ISkillItem iSkillItem) {
                iSkillItem.tickSkill(player, itemStack);
            }
        });
        player.getInventory().armor.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                if (!(item instanceof ICurioItem)) {
                    iCooldownItem.tickCooldown(player, itemStack);
                }
            }
            if (item instanceof ISkillItem iSkillItem) {
                iSkillItem.tickSkill(player, itemStack);
            }
        });
        player.getInventory().offhand.forEach(itemStack -> {
            Item item = itemStack.getItem();
            if (item instanceof ICooldownItem iCooldownItem) {
                if (!(item instanceof ICurioItem)) {
                    iCooldownItem.tickCooldown(player, itemStack);
                }
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
        if (EntityUtils.isInDimension(player, OHDimensions.NIGHTMARE)) {
            player.addEffect(new MobEffectInstance(OHMobEffects.NIGHTMARE_POSSESSED.get(), Utils.secondsToTicks(30)));
        }
        if (EntityUtils.isInDimension(player, Level.OVERWORLD)) {
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
            if (EntityUtils.isInDimension(player, Level.OVERWORLD)) {
                if (player.getMainHandItem().is(OHItems.Utilities.BLOOD_BEAR.get())) {
                    EntityUtils.travelToDimension(serverPlayer, OHDimensions.NIGHTMARE);
                    ServerLevel nextLevel = serverPlayer.server.getLevel(OHDimensions.NIGHTMARE);
                    if (nextLevel != null) {
                        EntityUtils.spreadEntity(nextLevel, serverPlayer, new Vec2(0, 0), 128, 192, 128);
                    }
                }
            } else if (EntityUtils.isInDimension(player, OHDimensions.NIGHTMARE)) {
                if (player.getMainHandItem().is(OHItems.Utilities.BLOOD_BEAR.get())) {
                    EntityUtils.travelToDimension(serverPlayer, OHDimensions.DYSTOPIA);
                    ServerLevel nextLevel = serverPlayer.server.getLevel(OHDimensions.DYSTOPIA);
                    if (nextLevel != null) {
                        EntityUtils.spreadEntity(nextLevel, serverPlayer, new Vec2(0, 0), 128, 192, 128);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTickLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level.dimension().compareTo(OHDimensions.NIGHTMARE) == 0) {
            level.setRainLevel(3.0f);
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
            } else {
                event.setCanceled(true);
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
        int difficultiesId = OHDataManager.modDifficulties.getId();
        if (difficultiesId > ModDifficulties.DEATH.getId()) {
            double armor = targetEntity.getAttributeValue(Attributes.ARMOR);
            double armorToughness = targetEntity.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            double useArmor = armor / 3 * difficultiesId;
            if (damageAmount < useArmor) {
                damageAmount = (float) Math.max(Math.min(2 * difficultiesId, damageAmount * 0.2) - 2 * (100 + armorToughness) / 200.0, 0);
            } else {
                damageAmount = (float) Math.max(damageAmount - useArmor * (100 + armorToughness) / 200.0, 0);
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
            damageAmount = damageAmount - shieldAbsorb;
            if (shieldValue < shieldAbsorb) {
                damageAmount += (float) (shieldAbsorb - shieldValue);
                ModCapabilities.getOhAttribute(player).setShieldValue(0);
            } else {
                ModCapabilities.getOhAttribute(player).setShieldValue(shieldValue - shieldAbsorb);
            }
            event.setAmount(damageAmount);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        printDebugInfo(event);
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
                    attribute.removeModifier(ZOMBIE_SPEED_UP);
                    attribute.addTransientModifier(new AttributeModifier(ZOMBIE_SPEED_UP, "outland_horizon.zombie.speed_up", (1 - scale) * 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
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
        if (entity instanceof Player) {
            return;
        }
        CompoundTag persistentData = entity.getPersistentData();
        if (!persistentData.contains(OutlandHorizon.MOD_ID)) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean(DATA_INIT, false);
            persistentData.put(OutlandHorizon.MOD_ID, tag);
        }
        CompoundTag tag = entity.getPersistentData().getCompound(OutlandHorizon.MOD_ID);
        if (!tag.getBoolean(DATA_INIT)) {
            if (entity instanceof Enemy && entity instanceof Mob mob) {
                ModDifficulties.applyDifficultySettingsForEntity(mob);
                mob.heal(Float.MAX_VALUE);
            }
            tag.putBoolean(DATA_INIT, true);
        }
    }

    private static void printDebugInfo(LivingDamageEvent event) {
        if (DevelopEvents.isDebug) {
            LivingEntity entity = event.getEntity();
            MutableComponent entityName = Component.literal(entity.getDisplayName().getString());
            MutableComponent entityNameToolTip = Component.literal("UUID：" + entity.getUUID())
                    .append(Component.literal("坐标：" + entity.position()));
            entityName.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, entityNameToolTip)));
            ChatUtils.allPlayers(entityName.append("受到了" + event.getAmount() + "点伤害"));
        }
    }
}
