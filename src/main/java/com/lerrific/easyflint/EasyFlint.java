package com.lerrific.easyflint;

import java.util.Random;

import net.minecraft.block.BlockGravel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Am I doing this right? -\(o_o)/-
@Mod(modid = "easyflint", name = "Easy Flint", version = "1.0.0")
public class EasyFlint
{	
	public static final Random rng = new Random();

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
	@SubscribeEvent
	public void rightClickGravel(RightClickBlock event) {
		if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockGravel && !event.getEntityPlayer().isCreative() && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).isEmpty() && !event.getWorld().isRemote) {
			World world = event.getWorld();
			BlockPos pos = event.getPos();
			
            world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_GRAVEL_HIT, SoundCategory.BLOCKS, 0.8F, 1.1F);
			if (rng.nextInt(100) < 13) {
				world.destroyBlock(pos, false);
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + rng.nextDouble(), pos.getZ(), new ItemStack(Items.FLINT, 1)));
			}
		}
	}
}
