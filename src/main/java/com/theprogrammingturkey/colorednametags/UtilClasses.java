package com.theprogrammingturkey.colorednametags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class UtilClasses
{
	public static int clamp(int lower, int upper, int number)
	{
		if(number < lower)
			return lower;
		if(number > upper)
			return upper;
		return number;
	}

	public static void applyColorToTag(ItemStack stack, List<Integer> colors)
	{
		String name = "";
		if(colors.size() == 0)
		{
			name = TextFormatting.getTextWithoutFormattingCodes(stack.getDisplayName());
		}
		else
		{
			String nameTemp = stack.getDisplayName();
			int numberOfLetters = nameTemp.length() / colors.size();
			if(numberOfLetters == 0)
				return;

			for(int i = 0; i < nameTemp.length(); i++)
			{
				if(i % numberOfLetters == 0)
					name += ReflectionHelper.getPrivateValue(EnumDyeColor.class, EnumDyeColor.byMetadata(colors.get(UtilClasses.clamp(0, colors.size() - 1, i / numberOfLetters))), "chatColor", "field_176793_x");
				name += nameTemp.charAt(i);
			}
		}
		NBTTagCompound nbttagcompound1 = stack.getTagCompound().getCompoundTag("display");
		if(!nbttagcompound1.hasKey("Lore"))
			nbttagcompound1.setTag("Lore", new NBTTagList());
		if(nbttagcompound1.getTagId("Lore") == 9)
		{
			NBTTagList nbttaglist3 = nbttagcompound1.getTagList("Lore", 8);
			NBTTagString stringNBT;
			int index = -1;
			for(int i = 0; i < nbttaglist3.tagCount(); i++)
			{
				if(nbttaglist3.get(i).getId() == 8)
				{
					stringNBT = (NBTTagString) nbttaglist3.get(i);
					if(stringNBT.getString().equals("Tag Color Preview:"))
					{
						index = i + 1;
					}
				}
			}

			if(index != -1)
			{
				nbttaglist3.set(index, new NBTTagString(name));
			}
			else
			{
				nbttaglist3.appendTag(new NBTTagString("Tag Color Preview:"));
				nbttaglist3.appendTag(new NBTTagString(name));
			}
		}
	}

	public static String getColorNameFromTag(ItemStack stack)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(stack.hasTagCompound())
			nbt = stack.getTagCompound();
		final List<Integer> colors = new ArrayList<>();
		if(nbt.hasKey("DisplayColors"))
			colors.addAll(Arrays.stream(nbt.getIntArray("DisplayColors")).boxed().collect(Collectors.toList()));
		String nameTemp = stack.getDisplayName();
		if(nameTemp == null || nameTemp.isEmpty() || colors.size() == 0)
			return "";

		int numberOfLetters = nameTemp.length() / colors.size();
		if(numberOfLetters == 0)
			return "";
		String name = "";
		for(int i = 0; i < nameTemp.length(); i++)
		{
			if(i % numberOfLetters == 0)
				name += ReflectionHelper.getPrivateValue(EnumDyeColor.class, EnumDyeColor.byMetadata(colors.get(UtilClasses.clamp(0, colors.size() - 1, i / numberOfLetters))), "chatColor", "field_176793_x");
			name += nameTemp.charAt(i);
		}

		return name;
	}
}
