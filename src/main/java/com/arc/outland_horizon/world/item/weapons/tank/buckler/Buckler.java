package com.arc.outland_horizon.world.item.weapons.tank.buckler;

import com.arc.outland_horizon.world.item.ICooldownItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class Buckler extends ShieldItem implements ICooldownItem {
    public static final String DAMAGE_BLOCKED = "damageBlocked";
    private final float maxBlockAmount;
    private final float blockRatio;
    private final int cooldownTime;

    public Buckler(Properties pProperties, float maxBlockAmount, float damageBlockRatio, int cooldownTime) {
        super(pProperties);
        this.maxBlockAmount = maxBlockAmount;
        this.blockRatio = damageBlockRatio;
        this.cooldownTime = cooldownTime;
    }

    public float maxBlockAmount() {
        return maxBlockAmount;
    }

    public float blockRatio() {
        return blockRatio;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.getOrCreateTag().putFloat(DAMAGE_BLOCKED, 0.0f);
        return super.initCapabilities(stack, nbt);
    }

    public float blockDamage(Player player, ItemStack itemStack, float damageAmount) {
        float hasBlocked = itemStack.getOrCreateTag().getFloat(DAMAGE_BLOCKED);
        float toBlock = damageAmount * blockRatio;
        float remain = damageAmount * (1 - blockRatio);
        if (hasBlocked + toBlock < maxBlockAmount) {
            itemStack.getOrCreateTag().putFloat(DAMAGE_BLOCKED, hasBlocked + toBlock);
            return damageAmount - remain;
        } else {
            float left = toBlock - (maxBlockAmount - hasBlocked) + remain;
            itemStack.getOrCreateTag().putFloat(DAMAGE_BLOCKED, 0);
            return damageAmount - left;
        }
    }

    @Override
    public int cooldownTime() {
        return cooldownTime;
    }
}
