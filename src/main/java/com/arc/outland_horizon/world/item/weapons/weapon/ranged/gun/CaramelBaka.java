package com.arc.outland_horizon.world.item.weapons.weapon.ranged.gun;

import com.arc.outland_horizon.utils.ChatUtils;
import com.arc.outland_horizon.utils.EntityUtils;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.DeveloperItem;
import com.arc.outland_horizon.world.sound.SoundEventRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CaramelBaka extends Gun implements DeveloperItem {
    public CaramelBaka() {
        super(2160, 20, Items.DIAMOND,new Properties().rarity(Rarity.create("caramel", style -> style.withColor(ChatFormatting.YELLOW).withBold(true))));
    }

    @Override
    public float getDamage() {
        return 65;
    }

    @Override
    public void onProjectileHitEntity(ThrowableProjectile projectile, Entity target, LivingEntity shooter) {
        EntityUtils.getEntitiesByRadio(target.level(),target.position(),5).forEach(entity -> {
            if(entity instanceof LivingEntity livingEntity){
                if(!livingEntity.is(target)){
                    EntityUtils.hurt(shooter, livingEntity, DamageTypes.MOB_ATTACK, getDamage()*0.65f);
                }
            }
        });
        super.onProjectileHitEntity(projectile, target, shooter);
    }

    @Override
    public int getCoolDown() {
        return Utils.secondsToTicks(1.5f);
    }

    @Override
    public int getBulletMaxAge() {
        return 8;
    }

    @Override
    public int getBulletVelocity() {
        return 10;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventRegister.GUN.get();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(ChatUtils.translatable("text.outland_horizon.gui.item.developer", developerName()).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public String developerName() {
        return "caramel";
    }
}
