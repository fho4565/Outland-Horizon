package com.arc.outland_horizon.world.item.tools;

import com.arc.outland_horizon.ModRarities;
import com.arc.outland_horizon.ModTiers;
import com.arc.outland_horizon.world.item.tools.multi.Paxel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ZeroReformer extends Paxel {
    public ZeroReformer() {
        super(ModTiers.VOID_CRYSTAL, new Properties().rarity(ModRarities.VOID).durability(4565));
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack pStack, LivingEntity pTarget, @Nonnull LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        return (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) ? Float.MAX_VALUE : 1.0F;
    }
}
