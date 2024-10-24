package com.arc.outland_horizon.world.item.weapons.weapon.melee;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.DeveloperItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class KaZhiSword extends AbstractMeleeWeapon implements DeveloperItem {
    int stage = 1;
    AttributeModifier kaZhiSword = new AttributeModifier(Utils.generateUUIDFromText("KaZhiSword"),"KaZhiSword", 0, AttributeModifier.Operation.ADDITION);
    Component stage1 = ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.stage_1").withStyle(ChatFormatting.AQUA);
    Component stage2 = ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.stage_2").withStyle(ChatFormatting.AQUA);
    Component stage3 = ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.stage_3").withStyle(ChatFormatting.AQUA);
    Component stage4 = ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.stage_4").withStyle(ChatFormatting.AQUA);


    public KaZhiSword() {
        super(2120, 4, -2.4f, 12, Items.ACACIA_PLANKS, new Properties().rarity(Rarity.create("ka_zhi", ChatFormatting.YELLOW)));
    }
    private int stage(ServerPlayer player){
        int stage = 0;
        Advancement killWither = Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getAdvancements().getAdvancement(new ResourceLocation("outland_horizon:minecraft_add/start")));
        Advancement killEnderDragon = Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getAdvancements().getAdvancement(new ResourceLocation("minecraft:end/kill_dragon")));
        if (player.getAdvancements().getOrStartProgress(killWither).isDone()) {
            stage = 1;
        }
        if (player.getAdvancements().getOrStartProgress(killEnderDragon).isDone()) {
            stage = 2;
        }
        this.stage = stage;
        return stage;
    }
    private int damageBonus(ServerPlayer player){
        int damage = 0;
        switch (stage(player)){
            case 1 -> damage = 8;
            case 2 -> damage = 15;
            case 3 -> damage = 22;
            case 4 -> damage = 38;
        }
        return damage;
    }
    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return super.hurtEnemy(itemStack, target, attacker);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int pSlotId, boolean pIsSelected) {
        if (entity instanceof ServerPlayer player) {
            AttributeInstance attribute = Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE));
            kaZhiSword = new AttributeModifier(Utils.generateUUIDFromText("KaZhiSword"),"KaZhiSword", damageBonus(player), AttributeModifier.Operation.ADDITION);
            if (pIsSelected) {
                if(attribute.getModifiers().stream().noneMatch(attributeModifier -> attributeModifier.getId().equals(Utils.generateUUIDFromText("KaZhiSword")))) {
                    attribute.addPermanentModifier(kaZhiSword);
                }
            }else{
                attribute.removeModifier(Utils.generateUUIDFromText("KaZhiSword"));
            }
        }
        super.inventoryTick(itemStack, level, entity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.hover_text").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.item.addition.stage",stage+1).withStyle(ChatFormatting.DARK_PURPLE));
        switch (stage+1){
            case 1 -> tooltipComponents.add(stage1);
            case 2 -> tooltipComponents.add(stage2);
            case 3 -> tooltipComponents.add(stage3);
            case 4 -> tooltipComponents.add(stage4);
        }
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.item.developer",developerName()).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.weapon.ka_zhi_sword.damage_add",kaZhiSword.getAmount()).withStyle(ChatFormatting.DARK_GREEN));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public String developerName() {
        return "caramel";
    }
}
