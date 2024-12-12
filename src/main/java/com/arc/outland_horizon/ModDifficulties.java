package com.arc.outland_horizon;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.world.entity.IBoss;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.IntFunction;

public enum ModDifficulties implements StringRepresentable {
    DISABLED(0, "disabled"),//关闭模组难度
    DEATH(1, "death"),//死亡模式
    TRIBULATION(2, "tribulation"),//苦难模式
    ETERNAL(3, "eternal");//永恒模式

    private static final AttributeModifier DEATH_MONSTER_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("07a81663-e795-31b6-a467-4287a0902ef9"), "outland_horizon.difficulty_death_attack_damage", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_MONSTER_HEALTH = new AttributeModifier(UUID.fromString("04579faf-55e9-3603-b8df-0fc8fce4bc16"), "outland_horizon.difficulty_death_health", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_BOSS_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("d3943ae4-4a5c-3f42-895f-7707f8b64841"), "outland_horizon.difficulty_death_attack_damage_boss", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_BOSS_HEALTH = new AttributeModifier(UUID.fromString("987a5bd5-8f92-34b2-a5c6-1f71b2c1f55f"), "outland_horizon.difficulty_death_health_boss", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final AttributeModifier TRIBULATION_MONSTER_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("288cfd83-b2b9-3f57-823f-e2ce56fc0480"), "outland_horizon.difficulty_tribulation_attack_damage", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_MONSTER_HEALTH = new AttributeModifier(UUID.fromString("d55201e6-1900-3b9e-ba3e-ce5cb0c5700f"), "outland_horizon.difficulty_tribulation_health", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_BOSS_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("ff288d9b-6674-3019-9e54-8c05736ef01b"), "outland_horizon.difficulty_tribulation_attack_damage_boss", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_BOSS_HEALTH = new AttributeModifier(UUID.fromString("96740631-1a0d-3d66-89b9-c085a303b6ea"), "outland_horizon.difficulty_tribulation_health_boss", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final AttributeModifier ETERNAL_MONSTER_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("b8ebec83-99c7-367e-b462-fc66ab2134ba"), "outland_horizon.difficulty_eternal_attack_damage", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_MONSTER_HEALTH = new AttributeModifier(UUID.fromString("4d5f6e17-f96e-3f52-acb5-a0210a1c0856"), "outland_horizon.difficulty_eternal_health", 0.6, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_BOSS_ATTACK_DAMAGE = new AttributeModifier(UUID.fromString("2e5a8694-4059-3cef-8a0b-e5d6ae16c02f"), "outland_horizon.difficulty_eternal_attack_damage_boss", 0.8, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_BOSS_HEALTH = new AttributeModifier(UUID.fromString("df472ecc-6444-37a1-bb57-53913fcb8cfc"), "outland_horizon.difficulty_eternal_health_boss", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);


    private static final IntFunction<ModDifficulties> BY_ID = ByIdMap.continuous(ModDifficulties::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;
    private final String key;

    ModDifficulties(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public static ModDifficulties byId(int id) {
        return BY_ID.apply(id);
    }

    public static void applyDifficultySettingsForEntity(LivingEntity entity) {
        if (entity instanceof Enemy) {
            ModDifficulties modDifficulties = OHDataManager.modDifficulties;
            AttributeInstance attackAttribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance healthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attackAttribute != null && healthAttribute != null) {
                attackAttribute.removeModifier(DEATH_MONSTER_ATTACK_DAMAGE);
                healthAttribute.removeModifier(DEATH_MONSTER_HEALTH);
                attackAttribute.removeModifier(TRIBULATION_MONSTER_ATTACK_DAMAGE);
                healthAttribute.removeModifier(TRIBULATION_MONSTER_HEALTH);
                attackAttribute.removeModifier(ETERNAL_MONSTER_ATTACK_DAMAGE);
                healthAttribute.removeModifier(ETERNAL_MONSTER_HEALTH);

                healthAttribute.removeModifier(DEATH_MONSTER_HEALTH);
                healthAttribute.removeModifier(DEATH_BOSS_HEALTH);
                healthAttribute.removeModifier(TRIBULATION_MONSTER_HEALTH);
                healthAttribute.removeModifier(TRIBULATION_BOSS_HEALTH);
                healthAttribute.removeModifier(ETERNAL_MONSTER_HEALTH);
                healthAttribute.removeModifier(ETERNAL_BOSS_HEALTH);

                switch (modDifficulties) {
                    case DEATH -> {
                        if (entity instanceof IBoss || (entity instanceof WitherBoss || entity instanceof EnderDragon)) {
                            attackAttribute.addTransientModifier(DEATH_BOSS_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(DEATH_BOSS_HEALTH);
                        } else {
                            attackAttribute.addTransientModifier(DEATH_MONSTER_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(DEATH_MONSTER_HEALTH);
                        }
                    }
                    case TRIBULATION -> {
                        if (entity instanceof IBoss || (entity instanceof WitherBoss || entity instanceof EnderDragon)) {
                            attackAttribute.addTransientModifier(TRIBULATION_BOSS_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(TRIBULATION_BOSS_HEALTH);
                        } else {
                            attackAttribute.addTransientModifier(TRIBULATION_MONSTER_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(TRIBULATION_MONSTER_HEALTH);
                        }
                    }
                    case ETERNAL -> {
                        if (entity instanceof IBoss || (entity instanceof WitherBoss || entity instanceof EnderDragon)) {
                            attackAttribute.addTransientModifier(ETERNAL_BOSS_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(ETERNAL_BOSS_HEALTH);
                        } else {
                            attackAttribute.addTransientModifier(ETERNAL_MONSTER_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(ETERNAL_MONSTER_HEALTH);
                        }
                    }
                }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public Component getDisplayName() {
        return switch (this.key) {
            case "disabled" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key).withStyle(ChatFormatting.AQUA);
            case "death" -> ChatUtils.translatable("options.oh_difficulty." + this.key).withStyle(ChatFormatting.GRAY);
            case "tribulation" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key).withStyle(ChatFormatting.RED);
            case "eternal" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key).withStyle(ChatFormatting.GOLD);
            default -> ChatUtils.translatable("options.oh_difficulty." + this.key);
        };
    }

    public Component getInfo() {
        return switch (this.key) {
            case "disabled" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key + ".info").withStyle(ChatFormatting.AQUA);
            case "death" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key + ".info").withStyle(ChatFormatting.GRAY);
            case "tribulation" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key + ".info").withStyle(ChatFormatting.RED);
            case "eternal" ->
                    ChatUtils.translatable("options.oh_difficulty." + this.key + ".info").withStyle(ChatFormatting.GOLD);
            default -> ChatUtils.translatable("options.oh_difficulty." + this.key + ".info");
        };
    }

    @Nonnull
    public String getSerializedName() {
        return this.key;
    }
}
