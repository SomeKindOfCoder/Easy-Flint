package com.lerrific.easyflint;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = EasyFlint.MODID, name = "Easy Flint", version = "1.1.0")
@Mod.EventBusSubscriber(modid = EasyFlint.MODID)
public class EasyFlint {
	public static final String MODID = "easyflint";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GravelEvent());
	}

	@SubscribeEvent
	public static void configChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(MODID)) {
			ConfigManager.sync(MODID, Config.Type.INSTANCE);
		}
	}

}
