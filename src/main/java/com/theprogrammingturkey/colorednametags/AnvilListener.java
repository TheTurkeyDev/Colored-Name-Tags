package com.theprogrammingturkey.colorednametags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.DyeUtils;

public class AnvilListener
{
	@SubscribeEvent
	public void onAvilUpdate(AnvilUpdateEvent e)
	{
		if(e.getLeft().getItem().equals(Items.NAME_TAG) && (e.getLeft().hasDisplayName() || !e.getName().isEmpty()))
		{
			if(DyeUtils.isDye(e.getRight()) || e.getRight().getItem().equals(Items.PAPER))
			{
				ItemStack stackOut = e.getLeft().copy();
				stackOut.setStackDisplayName(e.getName());
				NBTTagCompound nbt = new NBTTagCompound();
				if(stackOut.hasTagCompound())
					nbt = stackOut.getTagCompound();

				if(e.getRight().getItem().equals(Items.PAPER))
				{
					nbt.setIntArray("DisplayColors", new int[] {});
					stackOut.setTagCompound(nbt);
					UtilClasses.applyColorToTag(stackOut, new ArrayList<>());
					e.setOutput(stackOut);
					e.setResult(Result.ALLOW);
					e.setCost(1);
					return;
				}

				final List<Integer> colors = new ArrayList<>();
				if(nbt.hasKey("DisplayColors"))
					colors.addAll(Arrays.stream(nbt.getIntArray("DisplayColors")).boxed().collect(Collectors.toList()));
				DyeUtils.colorFromStack(e.getRight()).ifPresent(dyeCon -> colors.add(dyeCon.getMetadata()));
				nbt.setIntArray("DisplayColors", colors.stream().mapToInt(i -> i).toArray());
				stackOut.setTagCompound(nbt);

				UtilClasses.applyColorToTag(stackOut, colors);

				e.setOutput(stackOut);
				e.setCost(1);
				e.setResult(Result.ALLOW);
			}
		}
	}
}
