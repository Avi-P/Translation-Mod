package main.translation.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.translation.client.TranslationEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = TranslationMod.MODID, name = TranslationMod.NAME, version = TranslationMod.Version)
public class TranslationMod {
	
	public static final String MODID = "translationmod";
	public static final String NAME = "Translation Mod";
	public static final String Version = "1.0";
	public static final String acceptedMinecraftVersion = "1.12.2";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID.toUpperCase());
	
	@Mod.Instance(MODID)
	public static TranslationMod instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		
		MinecraftForge.EVENT_BUS.register(new TranslationEventHandler());
		
		LOGGER.info("REGISTERED");
		
    }
	
	public static void Postinit(FMLPostInitializationEvent event)
	{
		
	}
	
}
