package translation.client;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = TranslationMod.MOD_ID, name = TranslationMod.NAME, version = TranslationMod.Version, useMetadata = true)
public class TranslationMod {
	
	public static final String MOD_ID = "translationmod";
	public static final String NAME = "Translation Mod";
	public static final String Version = "1.0";
	public static final String acceptedMinecraftVersion = "1.12.2";
	
	
	@EventHandler
    public void init(FMLInitializationEvent event) throws IOException
    {
		
		MinecraftForge.EVENT_BUS.register(new TranslationEventHandler());
		
    }
	
}
