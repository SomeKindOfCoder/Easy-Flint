package com.lerrific.easyflint;

import java.util.Random;

import net.minecraft.block.BlockGravel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GravelEvent {
	private static final Random rng = new Random();
	private int timesClicked = 0;
	// I know the "progress" will be transferred across different gravel blocks, but,
	// do I care enough to make a complex system just for something so insignificant?
	// No... Well.. Kind of. But my laziness outweighs my perfectionism, thankfully.
	
	@SubscribeEvent
	public void searchGravel(RightClickBlock event) {
		if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockGravel && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).isEmpty() && !event.getWorld().isRemote) {
			if (!event.getEntityPlayer().isSneaking() && ModConfig.shouldPlayerSneak) {
			} else {
				World world = event.getWorld();
				EntityPlayer player = event.getEntityPlayer();
				BlockPos pos = event.getPos();

				world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_GRAVEL_HIT, SoundCategory.BLOCKS, 0.45F, 1.15F); // I don't know why we're casting PlayerEntity to null but hey that's what Minecraft did	
				if (ModConfig.shouldParticlesSpawn) {
					((WorldServer) world).spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, (double) ((float) pos.getX() + rng.nextFloat()), (double) (pos.getY() + 1), (double) ((float) pos.getZ() + rng.nextFloat()), timesClicked, 0.0D, 0.0D, 0.0D, 0.0D); // The casts here are also a mystery to me but hey that's how Minecraft did it
				}
				
				if (player.isPotionActive(MobEffects.HASTE)) { // Ternary operators suck >:(
					timesClicked += player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1 * 2; // Because haste 1 is actually 0!
				} else { timesClicked++; }
				
				if (rng.nextInt(100) < 33 && timesClicked > ModConfig.timesToClick * 2) { // Times 2 because this event gets fired twice, on client and on server i'm assuming.
					timesClicked = 0;
					world.destroyBlock(pos, false);
					if (ModConfig.shouldAlwaysDrop) {
						world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(), new ItemStack(Items.FLINT, getRandomNumberInRange(ModConfig.dropAmountMinimum, ModConfig.dropAmountMaximum))));
					} else {
						if (rng.nextInt(100) < ModConfig.dropChance) {
							world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(),new ItemStack(Items.FLINT, getRandomNumberInRange(ModConfig.dropAmountMinimum, ModConfig.dropAmountMaximum))));
						}
					}
				}
			}
		}
	}

	public static int getRandomNumberInRange(int min, int max) {
		return rng.nextInt((max - min) + 1) + min;
	}
}
