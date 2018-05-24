package com.theprogrammingturkey.colorednametags;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ColoredTagCore.MODID, version = ColoredTagCore.VERSION, name = ColoredTagCore.NAME, acceptableRemoteVersions = "*")
public class ColoredTagCore
{
	public static final String MODID = "colorednametags";
	public static final String NAME = "Colored Name Tags";
	public static final String VERSION = "@VERSION@";

	@Instance(value = MODID)
	public static ColoredTagCore instance;

	public static Logger logger;

	@EventHandler
	public void load(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		MinecraftForge.EVENT_BUS.register(new AnvilListener());
		MinecraftForge.EVENT_BUS.register(new EntityInteractListener());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
