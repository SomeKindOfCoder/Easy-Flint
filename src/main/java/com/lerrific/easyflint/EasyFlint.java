package com.lerrific.easyflint;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("easyflint")
public final class EasyFlint {
	public EasyFlint() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonConfig, "easyflint.toml");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        Config.loadConfig(Config.commonConfig, FMLPaths.CONFIGDIR.get().resolve("easyflint.toml"));
	}

	private void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new GravelEvent());
	}
}
