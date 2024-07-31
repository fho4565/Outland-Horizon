package com.isl.oh.items.weapons.melee.sword;

import com.isl.oh.items.weapons.AbstractWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AAASword extends AbstractWeapon {
    public AAASword() {
        super(96, 888, 15, Items.DIAMOND_AXE);
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return !p_41444_.isCreative();
    }
}
