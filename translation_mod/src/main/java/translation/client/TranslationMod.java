package translation.client;

import translation.settings.ClientSettings;
import translation.settings.ConfigChangeEvent;
import translation.settings.SettingParser;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = TranslationMod.MODID, name = TranslationMod.NAME, version = TranslationMod.Version, useMetadata = true)
public class TranslationMod {
	
	public static final String MODID = "translationmod";
	public static final String NAME = "Translation Mod";
	public static final String Version = "1.0";
	public static final String acceptedMinecraftVersion = "1.12.2";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
    {
		
		MinecraftForge.EVENT_BUS.register(new ClientSettings());
		ConfigManager.sync(MODID, Type.INSTANCE);
		SettingParser.configSetup();
		
    }
	
	@EventHandler
    public void init(FMLInitializationEvent event) throws IOException
    {
		
		MinecraftForge.EVENT_BUS.register(new ConfigChangeEvent());
		MinecraftForge.EVENT_BUS.register(new TranslationEventHandler());
		
    }
	
}
