package com.tsaroblivious.obliviousarchitecture.core.init;

import com.google.common.collect.ImmutableSet;
import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.blocks.HalfSlab;
import com.tsaroblivious.obliviousarchitecture.core.blocks.VerticalStairs;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			ObliviousArchitecture.MOD_ID);

	public static void createVerticalStairs() {
		ImmutableSet.of(Blocks.ACACIA_STAIRS, Blocks.ANDESITE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.BRICK_STAIRS,
				Blocks.COBBLESTONE_STAIRS, Blocks.DARK_OAK_STAIRS, Blocks.DARK_PRISMARINE_STAIRS, Blocks.DIORITE_STAIRS,
				Blocks.END_STONE_BRICK_STAIRS, Blocks.GRANITE_STAIRS, Blocks.JUNGLE_STAIRS,
				Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.NETHER_BRICK_STAIRS,
				Blocks.OAK_STAIRS, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_DIORITE_STAIRS,
				Blocks.POLISHED_GRANITE_STAIRS, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_BRICK_STAIRS,
				Blocks.PURPUR_STAIRS, Blocks.QUARTZ_STAIRS, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_SANDSTONE_STAIRS,
				Blocks.SANDSTONE_STAIRS, Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.SMOOTH_RED_SANDSTONE_STAIRS,
				Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.STONE_STAIRS, Blocks.STONE_BRICK_STAIRS,
				Blocks.BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS,
				Blocks.CRIMSON_STAIRS, Blocks.WARPED_STAIRS).forEach(
						b -> BLOCKS.register(
								b.getRegistryName().getPath().replace("_stairs", "_vertical_stairs")
										.replace("minecraft", "obliviousarchitecture"),
								() -> new VerticalStairs(AbstractBlock.Properties.copy(b))));
		ImmutableSet.of(Blocks.ACACIA_SLAB, Blocks.ANDESITE_SLAB, Blocks.BIRCH_SLAB, Blocks.BRICK_SLAB,
				Blocks.COBBLESTONE_SLAB, Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.DARK_OAK_SLAB,
				Blocks.DARK_PRISMARINE_SLAB, Blocks.DIORITE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB,
				Blocks.JUNGLE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB,
				Blocks.NETHER_BRICK_SLAB, Blocks.OAK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_DIORITE_SLAB,
				Blocks.POLISHED_GRANITE_SLAB, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.PURPUR_SLAB,
				Blocks.QUARTZ_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB,
				Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB,
				Blocks.SMOOTH_STONE_SLAB, Blocks.SPRUCE_SLAB, Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB,
				Blocks.BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
				Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB).forEach(
						b -> BLOCKS.register(
								b.getRegistryName().getPath().replace("_slab", "_half_slab").replace("minecraft",
										"obliviousarchitecture"),
								() -> new HalfSlab(AbstractBlock.Properties.copy(b))));

	}
}
