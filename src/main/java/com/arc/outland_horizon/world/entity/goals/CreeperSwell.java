package com.arc.outland_horizon.world.entity.goals;

import com.arc.outland_horizon.core.ModDataManager;
import com.arc.outland_horizon.core.ModDifficulties;
import com.arc.outland_horizon.utils.WorldUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

public class CreeperSwell extends TargetGoal {
    Creeper creeper;

    public CreeperSwell(Creeper creeper) {
        super(creeper, false);
        this.creeper = creeper;
    }

    @Override
    public void tick() {
        if (canUse()) {
            creeper.ignite();
        }
        super.tick();
    }

    @Override
    public boolean canUse() {
        List<Entity> entities = WorldUtils.getEntitiesByRadio(creeper.level(), creeper.position(), 6, entity -> entity instanceof ServerPlayer player && player.gameMode.isSurvival());
        return !entities.isEmpty() && ModDataManager.modDifficulties == ModDifficulties.ETERNAL;
    }
}
