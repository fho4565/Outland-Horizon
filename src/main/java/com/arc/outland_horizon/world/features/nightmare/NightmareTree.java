package com.arc.outland_horizon.world.features.nightmare;

import com.arc.outland_horizon.registry.OHBlocks;
import com.fho4565.brick_lib.tools.placer.Placer;
import com.fho4565.brick_lib.tools.placer.TreePlacer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.concurrent.ThreadLocalRandom;

public class NightmareTree extends Feature<NoneFeatureConfiguration> {
    public NightmareTree() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        TreePlacer treePlacer = TreePlacer.of(OHBlocks.Building.NIGHTMARE.NIGHTMARE_LOG.get().defaultBlockState(), OHBlocks.Building.NIGHTMARE.SCARRED_FLESH_BLOCK.get().defaultBlockState());
        treePlacer.randomFormerDirection();
        treePlacer.placeTrunk();
        treePlacer.moveAndPlaceTrunk(TreePlacer.MoveDirection.UP, UniformInt.of(3, 6));
        treePlacer.savepoint();
        if (random.nextBoolean()) {
            treePlacer.moveAndPlaceTrunk(Placer.MoveDirection.RIGHT);
            for (int j = 0; j < random.nextInt(3); j++) {
                treePlacer.move(Placer.MoveDirection.RIGHT).move(Placer.MoveDirection.UP);
                treePlacer.placeTrunk();
            }
        } else {
            treePlacer.moveAndPlaceTrunk(Placer.MoveDirection.LEFT);
            for (int j = 0; j < random.nextInt(3); j++) {
                treePlacer.move(Placer.MoveDirection.LEFT).move(Placer.MoveDirection.UP);
                treePlacer.placeTrunk();
            }
        }
        treePlacer.rollback();
        for (int i = 0; i < 4; i++) {
            treePlacer.moveAndPlaceTrunk(TreePlacer.MoveDirection.UP, UniformInt.of(2, 4));
            treePlacer.savepoint();
            if (random.nextBoolean()) {
                treePlacer.moveAndPlaceTrunk(Placer.MoveDirection.RIGHT);
                for (int j = 0; j < random.nextInt(3); j++) {
                    treePlacer.move(Placer.MoveDirection.RIGHT).move(Placer.MoveDirection.UP);
                    treePlacer.placeTrunk();
                }
            } else {
                treePlacer.moveAndPlaceTrunk(Placer.MoveDirection.LEFT);
                for (int j = 0; j < random.nextInt(3); j++) {
                    treePlacer.move(Placer.MoveDirection.LEFT).move(Placer.MoveDirection.UP);
                    treePlacer.placeTrunk();
                }
            }
            treePlacer.rollback();
        }
        return treePlacer.place(context.level(), context.origin());
    }
}
