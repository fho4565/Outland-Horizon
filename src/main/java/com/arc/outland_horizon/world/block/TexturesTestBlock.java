package com.arc.outland_horizon.world.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TexturesTestBlock extends Block {
    public static final EnumProperty<Texture> TYPE = EnumProperty.create("texture", Texture.class);

    public TexturesTestBlock(Properties blockBehaviour) {
        super(blockBehaviour);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, Texture.A));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(TYPE, Texture.A);
    }

    public enum Texture implements StringRepresentable {
        A('a'), B('b'), C('c'), D('d'), E('e'),
        F('f'), G('g'), H('h'), I('i'), J('j'),
        K('k'), L('l'), M('m'), N('n'), O('o'),
        P('p'), Q('q'), R('r'), S('s'), T('t'),
        U('u'), V('v'), W('w'), X('x'), Y('y'),
        Z('z');
        final char code;

        Texture(char code) {
            this.code = code;
        }

        @Override
        public @NotNull String getSerializedName() {
            return String.valueOf(code);
        }
    }


}
