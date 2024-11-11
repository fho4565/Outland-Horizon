package com.arc.outland_horizon.world.item.consumables.potions;

import com.arc.outland_horizon.world.item.consumables.Consumable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConsumablePotion extends Consumable {
    public ConsumablePotion(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 32;
    }
    public ArrayList<MobEffectInstance> getMobEffectInstance(){
        return new ArrayList<>();
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            getMobEffectInstance().forEach(player::addEffect);
            if(!player.getAbilities().instabuild){
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
