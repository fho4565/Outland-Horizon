package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.OHDataManager;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Zombie.class)
public abstract class ZombieMixin {
    @Shadow
    public abstract boolean isBaby();

    /**
     * @author fho4565
     * @reason 使小僵尸在白天不再燃烧
     */
    @Overwrite
    protected boolean isSunSensitive() {
        if (OHDataManager.modDifficulties.getId() > 0) {
            return !this.isBaby();
        }
        return true;
    }
}
