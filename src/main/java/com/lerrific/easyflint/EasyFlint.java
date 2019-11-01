package com.lerrific.easyflint;

import java.util.Random;

import net.minecraft.block.GravelBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("easyflint")
public final class EasyFlint {
	public static final Random rng = new Random();

	public EasyFlint() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void rightClickGravel(RightClickBlock event) {
		if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof GravelBlock && !event.getPlayer().isCreative() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).isEmpty() && !event.getWorld().isRemote) {
			World world = event.getWorld();
			BlockPos pos = event.getPos();
			
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_GRAVEL_HIT, SoundCategory.BLOCKS, 0.8F, 1.1F);
			if (rng.nextInt(100) < 13) {
				world.destroyBlock(pos, false);
				world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(), new ItemStack(Items.FLINT, 1)));
			}
		}
	}
}
