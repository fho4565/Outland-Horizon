package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.CapabilityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.Skill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ISkillItem {
    String SKILLS_TAG = "skills";
    String DURATION = "duration";
    String COOLDOWN = "cooldown";

    Skill skill1();

    default Skill skill2() {
        return null;
    }

    default Skill currentSkill(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID).getInt("skill") == 2 ? skill2() : skill1();
    }

    default CompoundTag currentSkillTag(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID);
        return tag.getInt("skill") == 1 ? tag.getCompound("skill1") : tag.getCompound("skill2");
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
        skill1Tag.putInt(DURATION, skill1().duration());
        skill1Tag.putInt(COOLDOWN, skill1().cooldown());
        root.put("skill1", skill1Tag);
        if (skill2() != null) {
            CompoundTag skill2Tag = new CompoundTag();
            skill2Tag.putInt(DURATION, skill2().duration());
            skill2Tag.putInt(COOLDOWN, skill2().cooldown());
            root.put("skill2", skill2Tag);
        }
        root.putInt("skill", 1);
    }

    default void switchSkill(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag().getCompound(OutlandHorizon.MOD_ID);
        if (skill2() != null && tag.getInt("skill") == 1) {
            tag.putInt("skill", 2);
        } else {
            tag.putInt("skill", 1);
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
        if (validateTags(itemStack)) {
            Skill skill = currentSkill(itemStack);
            int currentSkillCooldown = getCurrentSkillCooldown(itemStack);
            int currentSkillDuration = getCurrentSkillDuration(itemStack);
            if (skill.autoReduceDuration()) {
                reduceDuration(player, itemStack);
            }
            if (currentSkillCooldown > 0) {
                currentSkillTag(itemStack).putInt(COOLDOWN, --currentSkillCooldown);
            }
            if (currentSkillDuration > 0) {
                skill.onSkillTick(player, itemStack);
            }
        }
    }

    default boolean isInCooldown(ItemStack itemStack) {
        return getCurrentSkillCooldown(itemStack) > 0;
    }

    default boolean isInActive(ItemStack itemStack) {
        return getCurrentSkillDuration(itemStack) > 0;
    }

    default boolean canTriggerSkill(Player player, ItemStack itemStack) {
        return !isInCooldown(itemStack) && !isInActive(itemStack) && currentSkill(itemStack).canTrigger(player);
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
     * 冷却条的长度，默认以45像素为基准变化
     */
    default int skillCooldownBarWidth(ItemStack stack) {
        return Utils.getScaledBarWidth(45.0f, (float) getCurrentSkillCooldown(stack) / currentSkill(stack).cooldown());
    }

    default int skillDurationBarWidth(ItemStack stack) {
        return Utils.getScaledBarWidth(45.0f, (float) getCurrentSkillDuration(stack) / currentSkill(stack).duration());
    }

    default boolean shouldRenderSkillCooldownBar(ItemStack itemStack) {
        return true;
    }

    default boolean shouldRenderSkillDurationBar(ItemStack itemStack) {
        return true;
    }

}
