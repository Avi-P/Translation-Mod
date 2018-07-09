package translation.settings;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = "translationmod", type = Type.INSTANCE, name = "translationmod" + "_config")
public class ClientSettings {
	
	
	@Name("Language you want texts to be translated into")
	public static String mainLanguage = "en"; 
	
	@Name("Languages you want translation from")
	@Comment({"Use ISO 639-1 Code to define your languages.",
			"Enter codes seperated by a comma and space",
			"Example: 'en, es, fr'",
			"As you add more languages, the more cluttered your chat will be"})
	@RequiresMcRestart
	public static String translateLanguages = "";
	
	@Name("Character right before player text")
	public static String delimiter = ">";
	
}
