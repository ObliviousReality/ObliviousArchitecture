package com.tsaroblivious.obliviousarchitecture.core.init;

import com.tsaroblivious.obliviousarchitecture.ObliviousArchitecture;
import com.tsaroblivious.obliviousarchitecture.common.te.AnvilTileEntity;
import com.tsaroblivious.obliviousarchitecture.common.te.SawingBenchTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, ObliviousArchitecture.MOD_ID);

	public static final RegistryObject<TileEntityType<SawingBenchTileEntity>> SAWINGBENCH_TILE_ENTITY = TILE_ENTITIES
			.register("sawing_bench", () -> TileEntityType.Builder
					.of(SawingBenchTileEntity::new, RegularBlockInit.SAWING_BENCH.get()).build(null));

	public static final RegistryObject<TileEntityType<AnvilTileEntity>> ANVIL_TILE_ENTITY = TILE_ENTITIES.register(
			"anvil", () -> TileEntityType.Builder.of(AnvilTileEntity::new, RegularBlockInit.ANVIL.get()).build(null));

}
