package com.tsaroblivious.obliviousarchitecture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tsaroblivious.obliviousarchitecture.core.init.BlockInit;
import com.tsaroblivious.obliviousarchitecture.core.init.ItemInit;
import com.tsaroblivious.obliviousarchitecture.core.itemgroup.ObliviousArchitectureItemGroup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("obliviousarchitecture")
@Mod.EventBusSubscriber(modid = ObliviousArchitecture.MOD_ID, bus = Bus.MOD)
public class ObliviousArchitecture {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "obliviousarchitecture";

	public ObliviousArchitecture() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		BlockInit.createVerticalStairs();
		BlockInit.BLOCKS.register(bus);
		ItemInit.ITEMS.register(bus);

		MinecraftForge.EVENT_BUS.register(this);
		final ClientSideOnlyModEventRegistrar csomer = new ClientSideOnlyModEventRegistrar(bus);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> csomer::registerClientOnlyEvents);
	}

	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			event.getRegistry()
					.register(new BlockItem(block,
							new Item.Properties().tab(ObliviousArchitectureItemGroup.OBLIVIOUS_ARCHITECTURE))
									.setRegistryName(block.getRegistryName()));
		});

	}


}
