package com.tsaroblivious.obliviousarchitecture.core.blocks;

import java.util.stream.Stream;

import com.tsaroblivious.obliviousarchitecture.common.recipe.HammeringRecipe;
import com.tsaroblivious.obliviousarchitecture.common.te.AnvilTileEntity;
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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ArchitectAnvil extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(11, 0, 4, 16, 2, 12), Block.box(10, 0, 5, 11, 2, 11),
			Block.box(4, 2, 5, 8, 3, 6), Block.box(4, 2, 10, 8, 3, 11), Block.box(4, 2, 6, 5, 3, 10),
			Block.box(10, 2, 10, 14, 3, 11), Block.box(10, 2, 5, 14, 3, 6), Block.box(13, 2, 6, 14, 3, 10),
			Block.box(2, 0, 4, 7, 2, 12), Block.box(7, 0, 5, 8, 2, 11), Block.box(8, 0, 6, 10, 2, 10),
			Block.box(5, 2, 6, 13, 10, 10), Block.box(5, 10, 4, 16, 11, 12), Block.box(13, 9, 6, 15, 10, 10),
			Block.box(13, 8, 6, 14, 9, 10), Block.box(5, 9, 10, 14, 10, 11), Block.box(1, 8, 9, 2, 10, 10),
			Block.box(1, 8, 6, 2, 10, 7), Block.box(4, 6, 7, 5, 7, 9), Block.box(-1.04, 8, 7, 2, 10, 9),
			Block.box(2, 7, 6, 5, 10, 10), Block.box(5, 9, 5, 14, 10, 6)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_E = Stream
			.of(Block.box(4.0, 0, 11, 12, 2, 16), Block.box(5.0, 0, 10, 11, 2, 11), Block.box(10, 2, 4, 11, 3, 8),
					Block.box(5.0, 2, 4, 6.0, 3, 8), Block.box(6.0, 2, 4, 10, 3, 5), Block.box(5.0, 2, 10, 6.0, 3, 14),
					Block.box(10, 2, 10, 11, 3, 14), Block.box(6.0, 2, 13, 10, 3, 14), Block.box(4.0, 0, 2, 12, 2, 7),
					Block.box(5.0, 0, 7, 11, 2, 8), Block.box(6.0, 0, 8, 10, 2, 10), Block.box(6.0, 2, 5, 10, 10, 13),
					Block.box(4.0, 10, 5, 12, 11, 16), Block.box(6.0, 9, 13, 10, 10, 15),
					Block.box(6.0, 8, 13, 10, 9, 14), Block.box(5.0, 9, 5, 6.0, 10, 14),
					Block.box(6.0, 8, 1, 7.0, 10, 2), Block.box(9, 8, 1, 10, 10, 2), Block.box(7.0, 6, 4, 9, 7, 5),
					Block.box(7.0, 8, -1, 9, 10, 2), Block.box(6.0, 7, 2, 10, 10, 5), Block.box(10, 9, 5, 11, 10, 14))
			.reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_S = Stream.of(Block.box(0.0, 0, 4, 5.0, 2, 12),
			Block.box(5.0, 0, 5, 6.0, 2, 11), Block.box(8.0, 2, 10, 12.0, 3, 11), Block.box(8.0, 2, 5, 12.0, 3, 6),
			Block.box(11.0, 2, 6, 12.0, 3, 10), Block.box(2.0, 2, 5, 6.0, 3, 6), Block.box(2.0, 2, 10, 6.0, 3, 11),
			Block.box(2.0, 2, 6, 3.0, 3, 10), Block.box(9.0, 0, 4, 14.0, 2, 12), Block.box(8.0, 0, 5, 9.0, 2, 11),
			Block.box(6.0, 0, 6, 8.0, 2, 10), Block.box(3.0, 2, 6, 11.0, 10, 10), Block.box(0.0, 10, 4, 11.0, 11, 12),
			Block.box(1.0, 9, 6, 3.0, 10, 10), Block.box(2.0, 8, 6, 3.0, 9, 10), Block.box(2.0, 9, 5, 11.0, 10, 6),
			Block.box(14.0, 8, 6, 15.0, 10, 7), Block.box(14.0, 8, 9, 15.0, 10, 10), Block.box(11.0, 6, 7, 12.0, 7, 9),
			Block.box(14.0, 8, 7, 17.0, 10, 9), Block.box(11.0, 7, 6, 14.0, 10, 10),
			Block.box(2.0, 9, 10, 11.0, 10, 11)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();
	private static final VoxelShape SHAPE_W = Stream.of(Block.box(4, 0, 0, 12, 2, 5), Block.box(5, 0, 5, 11, 2, 6),
			Block.box(5, 2, 8, 6, 3, 12), Block.box(10, 2, 8, 11, 3, 12), Block.box(6, 2, 11, 10, 3, 12),
			Block.box(10, 2, 2, 11, 3, 6), Block.box(5, 2, 2, 6, 3, 6), Block.box(6, 2, 2, 10, 3, 3),
			Block.box(4, 0, 9, 12, 2, 14), Block.box(5, 0, 8, 11, 2, 9), Block.box(6, 0, 6, 10, 2, 8),
			Block.box(6, 2, 3, 10, 10, 11), Block.box(4, 10, 0, 12, 11, 11), Block.box(6, 9, 1, 10, 10, 3),
			Block.box(6, 8, 2, 10, 9, 3), Block.box(10, 9, 2, 11, 10, 11), Block.box(9, 8, 14, 10, 10, 15),
			Block.box(6, 8, 14, 7, 10, 15), Block.box(7, 6, 11, 9, 7, 12), Block.box(7, 8, 14, 9, 10, 17),
			Block.box(6, 7, 11, 10, 10, 14), Block.box(5, 9, 2, 6, 10, 11)).reduce((v1, v2) -> {
				return VoxelShapes.joinUnoptimized(v1, v2, IBooleanFunction.OR);
			}).get();

	public ArchitectAnvil() {
		super(AbstractBlock.Properties.copy(Blocks.ANVIL));
		this.registerDefaultState(this.stateDefinition.any());
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult brtr) {
		TileEntity teIn = world.getBlockEntity(pos);
		if (teIn instanceof AnvilTileEntity) {
			AnvilTileEntity te = (AnvilTileEntity) teIn;
			ItemStack held = player.getItemInHand(hand);
			if (held.getItem() == ItemInit.HAMMER.get()) {
				NonNullList<ItemStack> items = te.getItems();
				for (final IRecipe<?> recipe : world.getRecipeManager().getAllRecipesFor(RecipeInit.HAMMERING_RECIPE)) {
					final HammeringRecipe hammerRecipe = (HammeringRecipe) recipe;
					if (hammerRecipe.isValid(items.get(0), items.get(1))) {
						dropItem(world, pos, hammerRecipe.getResultItem().copy());
						held.setDamageValue(held.getDamageValue() + 1);
						world.playSound((PlayerEntity) null, pos, SoundEvents.ANVIL_USE, SoundCategory.BLOCKS, 0.8f, 1f);
						te.clearContent();
						return ActionResultType.sidedSuccess(world.isClientSide);
					}
				}
				world.playSound((PlayerEntity) null, pos, SoundEvents.ANVIL_LAND, SoundCategory.BLOCKS, 0.5f, 1f);
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else {
				boolean added = te.addItem(held);
				if (!added) {
					ItemStack drop = te.popItem();
					dropItem(world, pos, drop);
				}
				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void playerDestroy(World p_180657_1_, PlayerEntity p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_,
			TileEntity p_180657_5_, ItemStack p_180657_6_) {
		// TODO Auto-generated method stub
		super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, p_180657_5_, p_180657_6_);
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
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
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
		return TileEntityInit.ANVIL_TILE_ENTITY.get().create();
	}

}
