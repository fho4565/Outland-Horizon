package com.arc.outland_horizon.world.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class NightmareDirt extends Block {
    public enum VascularType implements StringRepresentable {
        NONE("none"),
        LIGHT("light"),
        HEAVY("heavy");
        private final String name;

        VascularType(String name) {
            this.name = name;
        }
        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
    public static final EnumProperty<VascularType> vascularType = EnumProperty.create("vascular_type", VascularType.class);

    public NightmareDirt() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL));
        this.registerDefaultState(this.stateDefinition.any().setValue(vascularType, VascularType.NONE));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(vascularType);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(vascularType, VascularType.NONE);
    }
}
