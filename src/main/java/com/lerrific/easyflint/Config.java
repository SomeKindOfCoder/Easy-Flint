package com.lerrific.easyflint;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
	
	private Config() { throw new IllegalStateException("Utility class"); }
	  
	// Press CTRL + SHIFT + F in here and I will format your very existence in to a black hole.
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec commonConfig;
	
	public static ForgeConfigSpec.IntValue timesToClick;
	public static ForgeConfigSpec.IntValue dropChance;
	public static ForgeConfigSpec.IntValue dropAmountMin;
	public static ForgeConfigSpec.IntValue dropAmountMax;
	
	public static ForgeConfigSpec.BooleanValue shouldPlayerSneak;
	public static ForgeConfigSpec.BooleanValue shouldAlwaysDrop;
	public static ForgeConfigSpec.BooleanValue shouldParticlesSpawn;

	static {
        COMMON_BUILDER.comment("General settings").push("general-settings");
        
		timesToClick = COMMON_BUILDER
				.comment("How many times should the player have to right click on gravel before flint can drop")
				.defineInRange("timesToClick", 5, 1, 1000);

		shouldPlayerSneak = COMMON_BUILDER
				.comment("Should the player have to sneak to search for flint")
				.define("shouldPlayerSneak", false);
		
		shouldParticlesSpawn = COMMON_BUILDER
				.comment("Should particles spawn when searching gravel")
				.define("shouldParticlesSpawn", true);
		
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Balancing settings").push("balancing-settings");

		shouldAlwaysDrop = COMMON_BUILDER
				.comment("Should flint always drop")
				.define("shouldAlwaysDrop", false);
		
		dropChance = COMMON_BUILDER
				.comment("If \"shouldAlwaysDrop\" is false, what is the chance of flint dropping")
				.defineInRange("dropChance", 75, 1, 100);
		
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Just pretend this is in balancing settings").push("forge-makes-me-cry");
        
		dropAmountMin = COMMON_BUILDER
				.comment("Minimum amount of flint to drop")
				.defineInRange("dropAmountMin", 1, 1, 63);
		
		dropAmountMax = COMMON_BUILDER
				.comment("Maximum amount of flint to drop")
				.defineInRange("dropAmountMax", 2, 1, 64);

        COMMON_BUILDER.pop();
        
        
		commonConfig = COMMON_BUILDER.build();
	}

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }
}
