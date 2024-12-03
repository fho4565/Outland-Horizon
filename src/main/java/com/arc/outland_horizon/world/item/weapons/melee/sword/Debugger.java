package com.arc.outland_horizon.world.item.weapons.melee.sword;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

public class Debugger extends Item {
    public Debugger() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        MinecraftServer server = target.level().getServer();
        if (server != null) {
            ResourceLocation resourcelocation = target.getLootTable();
            LootTable loottable = server.getLootData().getLootTable(resourcelocation);
            DamageSource lastDamageSource = target.getLastDamageSource();
            LootParams.Builder lootparams$builder = null;
            if (lastDamageSource != null) {
                lootparams$builder = (new LootParams.Builder((ServerLevel) target.level())).withParameter(LootContextParams.THIS_ENTITY, target).withParameter(LootContextParams.ORIGIN, target.position()).withParameter(LootContextParams.DAMAGE_SOURCE, lastDamageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, lastDamageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, lastDamageSource.getDirectEntity());
            }
            LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
            loottable.getRandomItems(lootparams, target.getLootTableSeed(), target::spawnAtLocation);
        }
        return super.hurtEnemy(itemStack, target, attacker);
    }
}
