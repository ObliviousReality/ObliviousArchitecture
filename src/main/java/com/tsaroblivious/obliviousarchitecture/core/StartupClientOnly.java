package com.tsaroblivious.obliviousarchitecture.core;

import com.tsaroblivious.obliviousarchitecture.core.init.BlockInit;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	@SubscribeEvent
	public static void onClientStartupEvent(FMLClientSetupEvent event) {
		BlockInit.BLOCKS.getEntries().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
	}
}