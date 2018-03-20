package com.theprogrammingturkey.colorednametags;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityInteractListener
{

	@SubscribeEvent
	public void onPlayerEntityInteract(EntityInteract e)
	{
		if(e.getItemStack().getItem().equals(Items.NAME_TAG))
		{
			String name = UtilClasses.getColorNameFromTag(e.getItemStack());
			if(!name.isEmpty() && !(e.getTarget() instanceof EntityPlayer))
			{
				e.getTarget().setCustomNameTag(name);

				if(e.getTarget() instanceof EntityLiving)
				{
					((EntityLiving) e.getTarget()).enablePersistence();
				}

				e.getItemStack().func_190918_g(1);
				e.setCanceled(true);
			}
		}
	}
}
