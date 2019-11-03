package com.lerrific.easyflint;

import java.util.Random;

import net.minecraft.block.GravelBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GravelEvent {
	private static final Random rng = new Random();
	private int timesClicked = 0;
	// I know the "progress" will be transferred across different gravel blocks, but,
	// do I care enough to make a complex system just for something so insignificant?
	// No... Well.. Kind of. But my laziness outweighs my perfectionism, thankfully.

	@SubscribeEvent
	public void searchGravel(RightClickBlock event) {
		if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof GravelBlock && event.getPlayer().getHeldItem(Hand.MAIN_HAND).isEmpty() && !event.getWorld().isRemote) {
			if (!event.getPlayer().isSneaking() && Boolean.TRUE.equals(Config.shouldPlayerSneak.get())) {
			} else {
				World world = event.getWorld();
				PlayerEntity player = event.getPlayer();
				BlockPos pos = event.getPos();

				world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_GRAVEL_HIT, SoundCategory.BLOCKS, 0.45F, 1.15F); // I don't know why we're casting PlayerEntity to null but hey that's what Minecraft did
				
				if (Boolean.TRUE.equals(Config.shouldParticlesSpawn.get())) {
					((ServerWorld) world).spawnParticle(ParticleTypes.MYCELIUM, (double) ((float) pos.getX() + rng.nextFloat()), (double) (pos.getY() + 1), (double) ((float) pos.getZ() + rng.nextFloat()), timesClicked,
							0.0D, 0.0D, 0.0D, 0.0D); // The casts here are also a mystery to me but hey that's how Minecraft did it
				}
				
				if (player.isPotionActive(Effects.HASTE)) { // Ternary operators suck >:(
					timesClicked += player.getActivePotionEffect(Effects.HASTE).getAmplifier() + 1 * 2; // Because haste 1 is actually 0!
				} else {
					timesClicked++;
				}
				
				if (rng.nextInt(100) < 33 && timesClicked > Config.timesToClick.get() * 2) { // Times 2 because this event gets fired twice, on client and on server i'm assuming.
					timesClicked = 0;
					world.destroyBlock(pos, false);
					if (Boolean.TRUE.equals(Config.shouldAlwaysDrop.get())) {
						world.addEntity(
								new ItemEntity(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(), new ItemStack(Items.FLINT, getRandomNumberInRange(Config.dropAmountMin.get(), Config.dropAmountMax.get()))));
					} else {
						if (rng.nextInt(100) < Config.dropChance.get()) {
							world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(),
									new ItemStack(Items.FLINT, getRandomNumberInRange(Config.dropAmountMin.get(), Config.dropAmountMax.get()))));
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
