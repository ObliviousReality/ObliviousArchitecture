package com.tsaroblivious.obliviousarchitecture.core.blocks;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class VerticalStairs extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(8, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 8))
			.reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_E = Stream.of(Block.box(0, 0, 8, 8, 16, 16), Block.box(8, 0, 0, 16, 16, 16))
			.reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_S = Stream.of(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 0, 8, 16, 16, 16))
			.reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_W = Stream.of(Block.box(8, 0, 0, 16, 16, 8), Block.box(0, 0, 0, 8, 16, 16))
			.reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public VerticalStairs(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
		case EAST:
			return SHAPE_E;
		case NORTH:
			return SHAPE_N;
		case SOUTH:
			return SHAPE_S;
		case WEST:
			return SHAPE_W;
		default:
			return SHAPE_N;
		}
	}

	@Override
	public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
		return 0.6f;
	}

}
