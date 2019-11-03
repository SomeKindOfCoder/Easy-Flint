package com.lerrific.easyflint;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;

@Config(modid = EasyFlint.MODID)
public class ModConfig {

	private ModConfig() {
		throw new IllegalStateException("Utility class");
	}

	@Config.Comment("How many times should the player have to right click on gravel before flint can drop")
	public static int timesToClick = 5;

	@Config.Comment("Should the be player required to sneak to search gravel for flint")
	public static boolean shouldPlayerSneak = false;

	@Config.Comment("Should particles spawn when searching gravel")
	public static boolean shouldParticlesSpawn = true;

	@Config.Comment("Should flint always drop")
	public static boolean shouldAlwaysDrop = true;

	@Config.Comment("If \"shouldAlwaysDrop\" is false, what is the chance of flint dropping")
	@Config.RangeInt(min = 1, max = 100)
	public static int dropChance = 75;

	@Config.Comment("Minimum amount of flint to drop")
	@Config.RangeInt(min = 1, max = 63)
	public static int dropAmountMinimum = 1;

	@Config.Comment("Maximum amount of flint to drop")
	@Config.RangeInt(min = 1, max = 64)
	public static int dropAmountMaximum = 2;
}
