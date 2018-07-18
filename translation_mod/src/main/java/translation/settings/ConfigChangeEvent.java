package translation.settings;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import translation.client.TranslationMod;

public class ConfigChangeEvent {
	
	/*
	 * Resyncs the config when the user changes something.
	 * Also calls configSetup() in SettingParser so that
	 * the new config is parsed again. 
	 */
	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event) {
		
		if (event.getModID().equals(TranslationMod.MODID)) {
	            ConfigManager.sync(TranslationMod.MODID, Type.INSTANCE);
		}
		
		SettingParser.configSetup();	//Parses new config data to handle any errors
		
	}
	 
}
