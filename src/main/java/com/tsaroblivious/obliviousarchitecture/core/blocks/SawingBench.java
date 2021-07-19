package com.tsaroblivious.obliviousarchitecture.core.blocks;

import java.util.Arrays;

import com.tsaroblivious.obliviousarchitecture.common.te.SawingBenchTileEntity;
import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SawingBench extends Block {

	public static final BooleanProperty HASBLOCK = BooleanProperty.create("hasblock");
	public static final IntegerProperty BLOCKLEVEL = IntegerProperty.create("blocklevel", 0, 8);

	private static final Item[] LOGS = { Items.OAK_LOG, Items.JUNGLE_LOG, Items.SPRUCE_LOG, Items.BIRCH_LOG,
			Items.DARK_OAK_LOG, Items.ACACIA_LOG };

	private static final Item[] PLANKS = { Items.OAK_PLANKS, Items.JUNGLE_PLANKS, Items.SPRUCE_PLANKS,
			Items.BIRCH_PLANKS, Items.DARK_OAK_PLANKS, Items.ACACIA_PLANKS };

	public SawingBench() {
		super(AbstractBlock.Properties.copy(Blocks.STONECUTTER));
		this.registerDefaultState(this.stateDefinition.any().setValue(HASBLOCK, false).setValue(BLOCKLEVEL, 0));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult brtr) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof SawingBenchTileEntity) {
			if (state.getValue(HASBLOCK)) {
				((SawingBenchTileEntity) te).outputSlotItem();
			} else {
				if (Arrays.asList(PLANKS).contains(player.getItemInHand(hand).getItem())
						|| Arrays.asList(LOGS).contains(player.getItemInHand(hand).getItem())) {
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
					double d0 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
					double d1 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
					double d2 = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
					ItemStack itemstack = te2.getItem();
					ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1,
							(double) pos.getZ() + d2, itemstack);
					itementity.setDefaultPickUpDelay();
					world.addFreshEntity(itementity);
					te2.clearItem();
				}
			}
		}
		super.playerDestroy(world, player, pos, state, te, itemStack);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(HASBLOCK, false).setValue(BLOCKLEVEL, 0);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(HASBLOCK);
		builder.add(BLOCKLEVEL);
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
