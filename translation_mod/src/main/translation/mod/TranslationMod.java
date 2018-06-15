package main.translation.mod;

import main.translation.translate.TranslationEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = TranslationMod.MODID, name = TranslationMod.NAME, version = TranslationMod.Version)
public class TranslationMod {
	
	public static final String MODID = "TranslationMod";
	public static final String NAME = "Translation Mod";
	public static final String Version = "1.0";
	public static final String acceptedMinecraftVersion = "1.12.2";
	
	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		MinecraftForge.EVENT_BUS.register(TranslationEventHandler.class);
		
        FMLCommonHandler.instance().bus().register(this);
        //MinecraftForge.EVENT_BUS.register(this);
    }
	
	
}
