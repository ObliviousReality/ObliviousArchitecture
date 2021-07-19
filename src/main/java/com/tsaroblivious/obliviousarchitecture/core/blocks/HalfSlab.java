package com.tsaroblivious.obliviousarchitecture.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class HalfSlab extends Block {

	public static final EnumProperty<HalfSlabType> TYPE = EnumProperty.create("type", HalfSlabType.class);
	protected static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 4, 16);
	protected static final VoxelShape TOP = Block.box(0, 12, 0, 16, 16, 16);
	protected static final VoxelShape DOUBLEBOTTOM = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape DOUBLETOP = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public HalfSlab(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.defaultBlockState().setValue(TYPE, HalfSlabType.BOTTOM));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		if (state.is(this)) {
			if (state.getValue(TYPE) == HalfSlabType.BOTTOM) {
				return state.setValue(TYPE, HalfSlabType.DOUBLEBOTTOM);
			} else {
				return state.setValue(TYPE, HalfSlabType.DOUBLETOP);
			}
		} else {
			Direction direction = context.getClickedFace();
			if (direction == Direction.DOWN) {
				return this.defaultBlockState().setValue(TYPE, HalfSlabType.TOP);
			} else {
				return this.defaultBlockState().setValue(TYPE, HalfSlabType.BOTTOM);
			}
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
		if (context.getItemInHand().getItem() == this.asItem()
				&& (state.getValue(TYPE) == HalfSlabType.BOTTOM || state.getValue(TYPE) == HalfSlabType.TOP)) {
			if (context.replacingClickedOnBlock()) {
				if (state.getValue(TYPE) == HalfSlabType.BOTTOM) {
					return context.getClickedFace() == Direction.UP;
				} else {
					return context.getClickedFace() == Direction.DOWN;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(TYPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext p_220053_4_) {
		switch (state.getValue(TYPE)) {
		case BOTTOM:
			return BOTTOM;
		case DOUBLEBOTTOM:
			return DOUBLEBOTTOM;
		case DOUBLETOP:
			return DOUBLETOP;
		case TOP:
			return TOP;
		default:
			return BOTTOM;
		}
	}

}
