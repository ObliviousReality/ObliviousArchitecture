package com.tsaroblivious.obliviousarchitecture.core.init;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.core.blocks.SawingBench;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegularBlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			ObliviousArchitecture.MOD_ID);

	public static RegistryObject<Block> SAWING_BENCH = BLOCKS.register("sawing_bench", () -> new SawingBench());
}
