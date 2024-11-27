package com.arc.outland_horizon.world.item;

import com.arc.outland_horizon.OutlandHorizon;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.Skill;
import net.minecraft.nbt.CompoundTag;
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
        if (tag.getInt("skill") == 1) {
            tag.putInt("skill", 2);
        } else {
            tag.putInt("skill", 1);
        }
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
    default int skillBarWidth(ItemStack stack) {
        return Utils.getScaledBarWidth(45.0f, (float) getCurrentSkillCooldown(stack) / currentSkill(stack).cooldown());
    }
}
