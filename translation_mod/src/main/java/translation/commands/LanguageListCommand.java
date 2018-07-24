package translation.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import translation.client.TranslationEventHandler;

/*
 * This command is used to see all the languages
 * supported for the /translate command. This list
 * of languages supported is different than
 * what is supported for real time translation.
 */
public class LanguageListCommand implements ICommand {
	
	private String languageList[] =    {"af", "Afrikaans", 	//List of all language codes and languages supported
										"am", "Amharic",
										"ar", "Arabic",
										"az", "Azeerbaijani",
										"be", "Belarusian",
										"bg", "Bulgarian",
										"bn", "Bengali",
										"bs", "Bosnian",
										"ca", "Catalan",
										"co", "Corsican",
										"cs", "Czech",
										"cy", "Welsh",
										"da", "Danish",
										"de", "German",
										"el", "Greek",
										"en", "English",
										"eo", "Esperanto",
										"es", "Spanish",
										"et", "Estonian",
										"eu", "Basque",
										"fa", "Persian",
										"fi", "Finnish",
										"fr", "French",
										"fy", "Frisian",
										"ga", "Irish",
										"gd", "Scots Gaelic",
										"gl", "Galician",
										"gu", "Gujarati",
										"ha", "Hausa",
										"hi", "Hindi",
										"hr", "Croatian",
										"ht", "Haitian Creole",
										"hu", "Hungarian",
										"hy", "Armenian",
										"id", "Indonesian",
										"ig", "Igbo",
										"is", "Icelandic",
										"it", "Italian",
										"iw", "Hebrew",
										"ja", "Japanese",
										"jw", "Javanese",
										"ka", "Georgian",
										"kk", "Kazakh",
										"km", "Khmer",
										"kn", "Kannada",
										"ko", "Korean",
										"ku", "Kurdish",
										"ky", "Kyrgyz",
										"la", "Latin",
										"lb", "Luxembourgish",
										"lo", "Lao",
										"lt", "Lithuanian",
										"lv", "Latvian",
										"mg", "Malagasy",
										"mi", "Maori",
										"mk", "Macedonian",
										"ml", "Malayalam",
										"mn", "Mongolian",
										"mr", "Marathi",
										"ms", "Malay",
										"mt", "Maltese",
										"my", "Myanmar",
										"ne", "Nepali",
										"nl", "Dutch",
										"no", "Norwegian",
										"ny", "Nyanja",
										"pa", "Punjabi",
										"pl", "Polish",
										"ps", "Pashto",
										"pt", "Portuguese",
										"ro", "Romanian",
										"ru", "Russian",
										"sd", "Sindhi",
										"si", "Sinhala",
										"sk", "Slovak",
										"sl", "Slovenian",
										"sm", "Samoan",
										"sn", "Shona",
										"so", "Somali",
										"sq", "Albanian",
										"sr", "Serbian",
										"st", "Sesotho",
										"su", "Sundanese",
										"sv", "Swedish",
										"sw", "Swahili",
										"ta", "Tamil",
										"te", "Telugu",
										"tg", "Tajik",
										"th", "Thai",
										"tl", "Tagalog",
										"tr", "Turkish",
										"uk", "Ukrainian",
										"ur", "Urdu",
										"uz", "Uzbek",
										"vi", "Vietnamese",
										"xh", "Xhosa",
										"yi", "Yiddish",
										"yo", "Yoruba",
										"zu", "Zulu"};
	
	@Override
	public int compareTo(ICommand arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {	//Name of command
		// TODO Auto-generated method stub
		return "languagelist";
	}

	@Override
	public String getUsage(ICommandSender sender) {	//Usage of the command
		// TODO Auto-generated method stub
		return "/languagelist <number>";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		List<String> aliases = new ArrayList<String>();
		aliases.add("/languagelist <number>");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		if(Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 21 ) {	//Ensures that only a number between 1 and 19 is entered
			TranslationEventHandler.outputMessage("List of Languages: Page " + args[0]);
			
			int startNumber = 10*(Integer.parseInt(args[0])-1);	//Start number for array access
			int endNumber = startNumber + 10;	//End number for array access
			
			if(endNumber > 198) {	//Makes there isn't an IndexOutOfBound error
				endNumber = 198;
			}
			
			for(int i = startNumber; i < endNumber; i = i + 2 ) {	//Used to print the languages
				ITextComponent outputComponent = new TextComponentString((i/2+1) + ". " + languageList[i] + " - " + languageList[i+1]);	//Formats output
				Minecraft.getMinecraft().player.sendMessage(outputComponent);
			}
			
		} else {
			TranslationEventHandler.outputMessage("Please enter a page number between 1 - 20.");
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
