package com.arc.outland_horizon;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.entity.IBoss;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;

import javax.annotation.Nonnull;
import java.util.function.IntFunction;

public enum ModDifficulties implements StringRepresentable {
    DISABLED(0, "disabled"),//关闭模组难度
    DEATH(1, "death"),//死亡模式
    TRIBULATION(2, "tribulation"),//苦难模式
    ETERNAL(3, "eternal");//永恒模式

    private static final AttributeModifier DEATH_MONSTER_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_death_attack_damage"), OutlandHorizon.MOD_ID + "difficulty_death_attack_damage", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_MONSTER_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_death_health"), OutlandHorizon.MOD_ID + "difficulty_death_health", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_BOSS_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_death_attack_damage_boss"), OutlandHorizon.MOD_ID + "difficulty_death_attack_damage_boss", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier DEATH_BOSS_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_death_health_boss"), OutlandHorizon.MOD_ID + "difficulty_death_health_boss", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final AttributeModifier TRIBULATION_MONSTER_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_tribulation_attack_damage"), OutlandHorizon.MOD_ID + "difficulty_tribulation_attack_damage", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_MONSTER_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_tribulation_health"), OutlandHorizon.MOD_ID + "difficulty_tribulation_health", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_BOSS_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_tribulation_attack_damage_boss"), OutlandHorizon.MOD_ID + "difficulty_tribulation_attack_damage_boss", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier TRIBULATION_BOSS_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_tribulation_health_boss"), OutlandHorizon.MOD_ID + "difficulty_tribulation_health_boss", 0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final AttributeModifier ETERNAL_MONSTER_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_eternal_attack_damage"), OutlandHorizon.MOD_ID + "difficulty_eternal_attack_damage", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_MONSTER_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_eternal_health"), OutlandHorizon.MOD_ID + "difficulty_eternal_health", 0.6, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_BOSS_ATTACK_DAMAGE = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_eternal_attack_damage_boss"), OutlandHorizon.MOD_ID + "difficulty_eternal_attack_damage_boss", 0.8, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ETERNAL_BOSS_HEALTH = new AttributeModifier(Utils.generateUUIDFromText(OutlandHorizon.MOD_ID + "difficulty_eternal_health_boss"), OutlandHorizon.MOD_ID + "difficulty_eternal_health_boss", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);


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
                        if (entity instanceof IBoss) {
                            attackAttribute.addTransientModifier(DEATH_BOSS_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(DEATH_BOSS_HEALTH);
                        } else {
                            attackAttribute.addTransientModifier(DEATH_MONSTER_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(DEATH_MONSTER_HEALTH);
                        }
                    }
                    case TRIBULATION -> {
                        if (entity instanceof IBoss) {
                            attackAttribute.addTransientModifier(TRIBULATION_BOSS_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(TRIBULATION_BOSS_HEALTH);
                        } else {
                            attackAttribute.addTransientModifier(TRIBULATION_MONSTER_ATTACK_DAMAGE);
                            healthAttribute.addTransientModifier(TRIBULATION_MONSTER_HEALTH);
                        }
                    }
                    case ETERNAL -> {
                        if (entity instanceof IBoss) {
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
