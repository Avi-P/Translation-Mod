package translation.settings;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;
import translation.client.TranslationMod;

@Config(modid = TranslationMod.MODID, type = Type.INSTANCE, name = TranslationMod.MODID + "_config")
public class ClientSettings {
	
	@Name("Language you want texts to be translated into")
	@Comment({"Enter in a two-letter ISO 639-1 Language Code",
			"Supported Codes: cs da de en es fi fr id it nl no pl pt ro sv tr vi"})
	public static String mainLanguage = "en"; 
	
	@Name("Languages you want translation from")
	@Comment({"Use ISO 639-1 Code to define your languages.",
			"Enter codes seperated by a space",
			"Example: 'en es fr'",
			"Supported Codes: cs da de en es fi fr id it nl no pl pt ro sv tr vi",
			"As you add more languages, the more cluttered your chat will become"})
	@RequiresMcRestart
	public static String translateLanguages = "en es fr";	
	
	@Name("Character right before player text")
	@Comment({"Acts as a way to determine whether the chat message",
			  "is a player message.",
			  "A single character is only accepted"})
	public static String delimiter = ">";
	
}
