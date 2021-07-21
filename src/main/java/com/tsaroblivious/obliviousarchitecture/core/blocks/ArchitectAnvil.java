package com.tsaroblivious.obliviousarchitecture.core.blocks;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.common.te.AnvilTileEntity;
import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ArchitectAnvil extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public static final BooleanProperty SLOT1 = BooleanProperty.create("slot1");
	public static final BooleanProperty SLOT2 = BooleanProperty.create("slot2");

	public ArchitectAnvil() {
		super(AbstractBlock.Properties.copy(Blocks.ANVIL));
		this.registerDefaultState(this.stateDefinition.any().setValue(SLOT1, false).setValue(SLOT2, false));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult brtr) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof AnvilTileEntity) {
			AnvilTileEntity te2 = (AnvilTileEntity) te;
			ItemStack held = player.getItemInHand(hand);
			ItemStack slot1 = te2.getSlot1();
			ItemStack slot2 = te2.getSlot2();
			boolean slot1Full = state.getValue(SLOT1);
			boolean slot2Full = state.getValue(SLOT2);
			if (player.isCrouching()) {
				if (slot2Full) {
					dropItem(world, pos, slot2);
					te2.clearSlot2();
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(SLOT2, false), 2);
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				} else if (slot1Full) {
					ObliviousArchitecture.LOGGER.debug("empty");
					dropItem(world, pos, slot1);
					te2.clearSlot1();
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(SLOT1, false), 2);
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				} else {
					return ActionResultType.PASS;
				}
			} else {
				if (held.sameItem(new ItemStack(ItemInit.HAMMER.get()))) { // craft
					if (slot1Full) {
						// hammer time
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				} else if (slot2Full) { // Full
					return ActionResultType.PASS;
				} else if (slot1Full) { // half full
					ItemStack item = new ItemStack(held.getItem(), 1);
					te2.setSlot2(item);
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(SLOT2, true), 2);
					}
					if (!player.abilities.instabuild) {
						held.shrink(1);
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				} else if (!held.sameItem(new ItemStack(Items.AIR, 1))) { // empty
					ItemStack item = new ItemStack(held.getItem(), 1);
					te2.setSlot1(item);
					if (!world.isClientSide) {
						world.setBlock(pos, state.setValue(SLOT1, true), 2);
					}
					if (!player.abilities.instabuild) {
						held.shrink(1);
					}
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
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
		return this.defaultBlockState().setValue(SLOT1, false).setValue(SLOT2, false).setValue(FACING,
				context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(SLOT1);
		builder.add(SLOT2);
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

//	@Override
//	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext p_220053_4_) {
//		switch (state.getValue(FACING)) {
//		case EAST:
//			return SHAPE_E;
//		case NORTH:
//			return SHAPE_N;
//		case SOUTH:
//			return SHAPE_S;
//		case WEST:
//			return SHAPE_W;
//		default:
//			return SHAPE_N;
//		}
//	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.ANVIL_TILE_ENTITY.get().create();
	}

}
