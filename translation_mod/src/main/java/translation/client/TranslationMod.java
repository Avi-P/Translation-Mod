package translation.client;

import translation.commands.CopyCommand;
import translation.commands.LanguageListCommand;
import translation.commands.TranslateCommand;
import translation.settings.ClientSettings;
import translation.settings.ConfigChangeEvent;
import translation.settings.SettingParser;

import java.io.IOException;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = TranslationMod.MODID, name = TranslationMod.NAME, version = TranslationMod.Version, useMetadata = true, canBeDeactivated = true)
public class TranslationMod {
	
	public static final String MODID = "translationmod";
	public static final String NAME = "Translation Mod";
	public static final String Version = "1.0";
	public static final String acceptedMinecraftVersion = "1.12.2";
	
	/*
	 * During PreInitialization, the config is synced up.
	 * This is needed because the language files needed 
	 * for language detection is only loaded up once
	 * a client session. Also the config data is
	 * parsed to prevent errors.
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
    {
		
		MinecraftForge.EVENT_BUS.register(new ClientSettings());
		ConfigManager.sync(MODID, Type.INSTANCE);
		SettingParser.configSetup();
		
    }
	
	/*
	 * This starts the actual event handler that makes up
	 * a large part of this Mod and a config sync event handler.
	 */
	@EventHandler
    public void init(FMLInitializationEvent event) throws IOException
    {
		ClientCommandHandler.instance.registerCommand(new TranslateCommand());
		ClientCommandHandler.instance.registerCommand(new CopyCommand());
		ClientCommandHandler.instance.registerCommand(new LanguageListCommand());
		MinecraftForge.EVENT_BUS.register(new ConfigChangeEvent());
		MinecraftForge.EVENT_BUS.register(new TranslationEventHandler());
		
    }
	
}
