package com.isl.outland_horizon.item.consumables;

import com.isl.outland_horizon.utils.ManaUtils;
import com.isl.outland_horizon.utils.Utils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class AbstractConsumables extends Item {
    private UseAnim useAnimation;
    private int useDuration = 40;
    public AbstractConsumables() {
        super(new Properties());
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if(p_41434_.equals(InteractionHand.MAIN_HAND)){
            p_41433_.startUsingItem(p_41434_);
        }
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 40;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if(p_41411_ instanceof Player player){
            ManaUtils.addMana(player, 100);
            Utils.sendSimpleMessageToPlayer(player, "[Mana]"+ManaUtils.getMana(player));
        }
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }
}
