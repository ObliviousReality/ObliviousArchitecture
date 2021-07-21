package com.tsaroblivious.obliviousarchitecture.core;

import com.tsaroblivious.obliviousarchitecture.common.entity.AnvilTileEntityRenderer;
import com.tsaroblivious.obliviousarchitecture.common.entity.SawingBenchTileEntityRenderer;
import com.tsaroblivious.obliviousarchitecture.core.init.TileEntityInit;
import com.tsaroblivious.obliviousarchitecture.core.init.WoodBlockInit;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	@SubscribeEvent
	public static void onClientStartupEvent(FMLClientSetupEvent event) {
		WoodBlockInit.BLOCKS.getEntries().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));

		ClientRegistry.bindTileEntityRenderer(TileEntityInit.SAWINGBENCH_TILE_ENTITY.get(),
				SawingBenchTileEntityRenderer::new);
		
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.ANVIL_TILE_ENTITY.get(),
				AnvilTileEntityRenderer::new);
	}
}
