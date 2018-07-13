package translation.settings;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import translation.client.TranslationMod;

public class ConfigChangeEvent {
	
	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event) {
		
		if (event.getModID().equals(TranslationMod.MODID)) {
	            ConfigManager.sync(TranslationMod.MODID, Type.INSTANCE);
		}
		
		SettingParser.configSetup();
		
	}
	 
}
