package com.arc.outland_horizon.mixins;

import com.arc.outland_horizon.OHDataManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Creeper.class)
public class CreeperMixin {
    @Redirect(method = "explodeCreeper", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"))
    private Explosion explodeCreeperMixin(Level instance, Entity source, double x, double y, double z, float radius, Level.ExplosionInteraction pExplosionInteraction) {
        float f = ((Creeper) source).isPowered() ? 2.0F : 1.0F;
        int id = OHDataManager.modDifficulties.getId();
        return instance.explode(source, x, y, z, (float) (radius * f * (1 + id * 0.25)), Level.ExplosionInteraction.MOB);
    }
}
