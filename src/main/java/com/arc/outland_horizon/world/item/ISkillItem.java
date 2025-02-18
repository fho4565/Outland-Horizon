package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.Skill;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface ISkillItem {
    String SKILLS_TAG = "skills";
    String DURATION = "duration";
    String COOLDOWN = "cooldown";
    String RENDER_SKILL_COOLDOWN_BAR = "render_skill_cooldown_bar";
    String RENDER_SKILL_DURATION_BAR = "render_skill_duration_bar";
    String TEXT = "text.outland_horizon.gui.weapon.skill.";

    Skill skill1();

    default Skill skill2() {
        return null;
    }

    default Skill currentSkill(ItemStack itemStack) {
        if (!validateTags(itemStack)) {
            initSkills(itemStack);
        }
        return itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID).getInt(SKILLS_TAG) == 2 ? skill2() : skill1();
    }

    default CompoundTag currentSkillTag(ItemStack itemStack) {
        if (!validateTags(itemStack)) {
            initSkills(itemStack);
        }
        CompoundTag tag = itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID);
        return tag.getInt(SKILLS_TAG) == 1 ? tag.getCompound("skill1") : tag.getCompound("skill2");
    }

    default void cooldownCurrentSkill(ItemStack itemStack) {
        currentSkillTag(itemStack).putInt(COOLDOWN, currentSkill(itemStack).cooldown());
    }

    default void initSkills(ItemStack itemStack) {
        if (!itemStack.getOrCreateTag().contains(OutlandHorizon.MOD_ID)) {
            itemStack.getOrCreateTag().put(OutlandHorizon.MOD_ID, new CompoundTag());
        }
        CompoundTag root = itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID);
        CompoundTag skill1Tag = new CompoundTag();
        skill1Tag.putInt(DURATION, 0);
        skill1Tag.putInt(COOLDOWN, 0);
        skill1Tag.putBoolean(RENDER_SKILL_DURATION_BAR, true);
        skill1Tag.putBoolean(RENDER_SKILL_COOLDOWN_BAR, true);
        root.put("skill1", skill1Tag);
        if (skill2() != null) {
            CompoundTag skill2Tag = new CompoundTag();
            skill2Tag.putInt(DURATION, 0);
            skill2Tag.putInt(COOLDOWN, 0);
            skill2Tag.putBoolean(RENDER_SKILL_DURATION_BAR, true);
            skill2Tag.putBoolean(RENDER_SKILL_COOLDOWN_BAR, true);
            root.put("skill2", skill2Tag);
        }
        root.putInt(SKILLS_TAG, 1);
    }

    default void switchSkill(ItemStack itemStack) {
        if (!validateTags(itemStack)) {
            initSkills(itemStack);
        }
        CompoundTag tag = itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID);
        if (skill2() != null && tag.getInt(SKILLS_TAG) == 1) {
            tag.putInt(SKILLS_TAG, 2);
        } else {
            tag.putInt(SKILLS_TAG, 1);
        }
    }

    default boolean validateTags(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.contains(OutlandHorizon.MOD_ID)) {
            return false;
        }
        CompoundTag root = tag.getCompound(OutlandHorizon.MOD_ID);
        if (skill2() != null) {
            if (root.contains("skill2")) {
                CompoundTag skill2 = root.getCompound("skill2");
                return skill2.contains(DURATION) && skill2.contains(COOLDOWN);
            }
            return false;
        }

        if (root.contains("skill1")) {
            CompoundTag skill1 = root.getCompound("skill1");
            return skill1.contains(DURATION) && skill1.contains(COOLDOWN);
        }

        return false;
    }

    default void reduceDuration(Player player, ItemStack itemStack) {
        Skill skill = currentSkill(itemStack);
        int currentSkillDuration = getCurrentSkillDuration(itemStack);
        if (currentSkillDuration > 0) {
            currentSkillTag(itemStack).putInt(DURATION, --currentSkillDuration);
            if (currentSkillDuration == 0) {
                skill.onSkillEnd(player, itemStack);
                cooldownCurrentSkill(itemStack);
            }
        }
    }

    default void tickSkill(Player player, ItemStack itemStack) {
        Skill skill = currentSkill(itemStack);
        int currentSkillCooldown = getCurrentSkillCooldown(itemStack);
        int currentSkillDuration = getCurrentSkillDuration(itemStack);
        if (skill.autoReduceDuration()) {
            reduceDuration(player, itemStack);
        }
        if (skill.autoCoolDown()) {
            if (currentSkillCooldown > 0) {
                currentSkillTag(itemStack).putInt(COOLDOWN, --currentSkillCooldown);
            }
        }
        if (currentSkillDuration > 0) {
            skill.onSkillTick(player, itemStack);
        }
    }

    default boolean isCurrentSkillCooldown(ItemStack itemStack) {
        return getCurrentSkillCooldown(itemStack) > 0;
    }

    default boolean isCurrentSkillActive(ItemStack itemStack) {
        return getCurrentSkillDuration(itemStack) > 0;
    }

    default boolean canTriggerSkill(Player player, ItemStack itemStack) {
        return !isCurrentSkillCooldown(itemStack) && !isCurrentSkillActive(itemStack) && currentSkill(itemStack).canTrigger(player);
    }

    default boolean triggerSkill(Player player, ItemStack itemStack) {
        if (canTriggerSkill(player, itemStack)) {
            Skill skill = currentSkill(itemStack);
            CapabilityUtils.Sp.removeSp(player, skill.requiredSp());
            currentSkillTag(itemStack).putInt(DURATION, skill.duration());
            skill.onSkillStart(player, itemStack);
            return true;
        }
        return false;
    }

    default int getCurrentSkillCooldown(ItemStack itemStack) {
        return currentSkillTag(itemStack).getInt(COOLDOWN);
    }

    default int getCurrentSkillDuration(ItemStack itemStack) {
        return currentSkillTag(itemStack).getInt(DURATION);
    }

    /**
     * 冷却条的颜色，默认像原版耐久条一样变化
     */
    default int skillBarColor(ItemStack stack) {
        return Utils.getColorForBar(1 - (float) getCurrentSkillCooldown(stack) / currentSkill(stack).cooldown());
    }

    /**
     * 冷却条的长度，默认以40像素为基准变化
     */
    default int skillCooldownBarWidth(ItemStack stack) {
        return Utils.getScaledBarWidth(45f, (float) getCurrentSkillCooldown(stack) / currentSkill(stack).cooldown());
    }

    default int skillDurationBarWidth(ItemStack stack) {
        return Utils.getScaledBarWidth(45.0f, (1 - (float) getCurrentSkillDuration(stack) / currentSkill(stack).duration()));
    }

    default boolean shouldRenderSkillCooldownBar(ItemStack itemStack) {
        return currentSkillTag(itemStack).getBoolean(RENDER_SKILL_COOLDOWN_BAR);
    }

    default void setShouldRenderSkillCooldownBar(ItemStack itemStack, boolean value) {
        currentSkillTag(itemStack).putBoolean(RENDER_SKILL_COOLDOWN_BAR, value);
    }

    default boolean shouldRenderSkillDurationBar(ItemStack itemStack) {
        return currentSkillTag(itemStack).getBoolean(RENDER_SKILL_DURATION_BAR);
    }

    default void setShouldRenderSkillDurationBar(ItemStack itemStack, boolean value) {
        currentSkillTag(itemStack).putBoolean(RENDER_SKILL_DURATION_BAR, value);
    }

    default List<Component> skillTooltip() {
        ArrayList<Component> components = new ArrayList<>();
        components.add(Component.empty());
        if (skill2() != null) {
            components.add(ChatUtils.translatable(TEXT + "label.name", "1").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill1().getId() + ".name", skill1().name())));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.description").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill1().getId() + ".description", skill1().description()))));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.sp_cost", skill1().requiredSp()).withStyle(ChatFormatting.DARK_GREEN)));
            if (skill1().autoReduceDuration()) {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.auto", skill1().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            } else {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.count", skill1().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            }
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.cooldown", skill1().cooldown()).withStyle(ChatFormatting.DARK_GREEN)));
            components.add(Component.empty());
            components.add(ChatUtils.translatable(TEXT + "label.name", "2").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill2().getId() + ".name", skill2().name())));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.description").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill2().getId() + ".description", skill2().description()))));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.sp_cost", skill2().requiredSp()).withStyle(ChatFormatting.DARK_GREEN)));
            if (skill2().autoReduceDuration()) {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.auto", skill2().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            } else {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.count", skill2().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            }
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.cooldown", skill2().cooldown()).withStyle(ChatFormatting.DARK_GREEN)));

        } else {
            components.add(ChatUtils.translatable(TEXT + "label.name", "").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill1().getId() + ".name", skill1().name())));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.description").withStyle(ChatFormatting.DARK_GREEN).append(ChatUtils.translatable(TEXT + skill1().getId() + ".description", skill1().description()))));
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.sp_cost", skill1().requiredSp()).withStyle(ChatFormatting.DARK_GREEN)));
            if (skill1().autoReduceDuration()) {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.auto", skill1().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            } else {
                components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.duration.count", skill1().duration()).withStyle(ChatFormatting.DARK_GREEN)));
            }
            components.add(Component.literal("   ").append(ChatUtils.translatable(TEXT + "label.cooldown", skill1().cooldown()).withStyle(ChatFormatting.DARK_GREEN)));
        }
        return components;
    }
}
