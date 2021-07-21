package com.tsaroblivious.obliviousarchitecture.core.blocks;

import com.tsaroblivious.obliviousarchitecture.common.recipe.SawingRecipe;
import com.tsaroblivious.obliviousarchitecture.common.te.SawingBenchTileEntity;
import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;
import com.tsaroblivious.obliviousarchitecture.core.init.RecipeInit;
import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SawingBench extends Block {

	public static final BooleanProperty HASBLOCK = BooleanProperty.create("hasblock");
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	private static final VoxelShape SHAPE_N = VoxelShapes.joinUnoptimized(Block.box(0, 14, 3, 16, 15, 13),
			Block.box(3, 0, 3, 13, 14, 13), IBooleanFunction.OR);

	private static final VoxelShape SHAPE_E = VoxelShapes.joinUnoptimized(Block.box(3, 14, 0, 13, 15, 16),
			Block.box(3, 0, 3, 13, 14, 13), IBooleanFunction.OR);

	private static final VoxelShape SHAPE_S = VoxelShapes.joinUnoptimized(Block.box(0, 14, 3, 16, 15, 13),
			Block.box(3, 0, 3, 13, 14, 13), IBooleanFunction.OR);

	private static final VoxelShape SHAPE_W = VoxelShapes.joinUnoptimized(Block.box(3, 14, 0, 13, 15, 16),
			Block.box(3, 0, 3, 13, 14, 13), IBooleanFunction.OR);

	public SawingBench() {
		super(AbstractBlock.Properties.copy(Blocks.STONECUTTER));
		this.registerDefaultState(this.stateDefinition.any().setValue(HASBLOCK, false));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult brtr) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof SawingBenchTileEntity) {
			if (state.getValue(HASBLOCK) && player.getItemInHand(hand).sameItem(new ItemStack(ItemInit.SAW.get()))) {
				SawingBenchTileEntity te2 = (SawingBenchTileEntity) te;
				ItemStack held = te2.getSlot();
				for (final IRecipe<?> recipe : world.getRecipeManager().getAllRecipesFor(RecipeInit.SAWING_RECIPE)) {
					final SawingRecipe sawingRecipe = (SawingRecipe) recipe;
					if (sawingRecipe.isValid(held)) {
						dropItem(world, pos, recipe.getResultItem().copy());
						player.getItemInHand(hand).setDamageValue(player.getItemInHand(hand).getDamageValue() + 1);
//						world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_FRAME_BREAK, SoundCategory.BLOCKS, 1, 1);
						te2.clearSlot();
						if (!world.isClientSide) {
							world.setBlock(pos, state.setValue(HASBLOCK, false), 3);
						}
					}
				}
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else if (state.getValue(HASBLOCK)) {
				SawingBenchTileEntity te2 = (SawingBenchTileEntity) te;
				ItemStack held = te2.getSlot();
				dropItem(world, pos, held);
				te2.clearSlot();
				if (!world.isClientSide) {
					world.setBlock(pos, state.setValue(HASBLOCK, false), 2);
				}
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else {
				if (!player.getItemInHand(hand).sameItem(new ItemStack(Items.AIR, 1))) {
					ItemStack item = new ItemStack(player.getItemInHand(hand).getItem(), 1);
					((SawingBenchTileEntity) te).setSlot(item);
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(HASBLOCK, true), 2);
					}
					if (!player.abilities.instabuild) {
						player.getItemInHand(hand).shrink(1);
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}

		return ActionResultType.PASS;
	}

	@Override
	public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te,
			ItemStack itemStack) {
		if (te instanceof SawingBenchTileEntity) {
			if (state.getValue(HASBLOCK)) {
				if (!world.isClientSide) {
					SawingBenchTileEntity te2 = (SawingBenchTileEntity) te;
					ItemStack itemstack = te2.getSlot();
					dropItem(world, pos, itemstack);
					te2.clearSlot();
				}
			}
		}
		super.playerDestroy(world, player, pos, state, te, itemStack);
	}

	private void dropItem(World world, BlockPos pos, ItemStack item) {
		double d0 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
		double d1 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
		double d2 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;

		ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1,
				(double) pos.getZ() + d2, item);
		itementity.setDefaultPickUpDelay();
		world.addFreshEntity(itementity);

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(HASBLOCK, false).setValue(FACING,
				context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(HASBLOCK);
		builder.add(FACING);
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext p_220053_4_) {
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
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.SAWINGBENCH_TILE_ENTITY.get().create();
	}

}
