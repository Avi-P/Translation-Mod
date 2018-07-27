package translation.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import translation.client.TranslationEventHandler;

/*
 * This command is used to that user can translate message
 * through the Minecraft chat. User enters input language,
 * output language, and text to be translated. Out comes
 * translated text.
 */
public class TranslateCommand implements ICommand {
	
	private static String translatedMessage = "";	//Static so that Copy Command can access
	
	@Override
	public int compareTo(ICommand arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {	//Name of command
		// TODO Auto-generated method stub
		return "translate";
	}

	@Override
	public String getUsage(ICommandSender sender) {	//Usage of the command
		// TODO Auto-generated method stub
		return "/translate <input language> <output language> <text>";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		List<String> aliases = new ArrayList<String>();
		aliases.add("/translate <input language> <output language> <text>");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		
		new Thread(() -> {	//New thread to overcome the blocking function when querying the server
			try {

				String inputLanguage = args[0];
				String outputLanguage = args[1];
				
				if(binarySearch(inputLanguage) == false || binarySearch(outputLanguage) == false) {
					TranslationEventHandler.outputMessage("Error. Could not translate.");
					return;
				}
				StringBuilder text = new StringBuilder();	//Used to build the string that will be translated
				
				for(int i = 2; i <= args.length-1; i++) {
					if(i != args.length-1) {
						text.append(args[i]+" ");
					} else {
						text.append(args[i]);
					}
				}
				
				String toBeTranslated = text.toString();	//Final text to be translated
				
				translatedMessage = TranslationEventHandler.getHttp().customTranslate(inputLanguage, outputLanguage, toBeTranslated);
				
				if(translatedMessage.equals("")) {	//This case indicates that nothing should be outputted to client
					return;
				}
				
				TranslationEventHandler.outputMessage("[Entered Text] " + toBeTranslated);	//Outputs original text
				TranslationEventHandler.outputMessage(translatedMessage);	//Outputs the translated text
				return;
				
			} catch (Exception e1) {
				e1.printStackTrace();	//Prints to Minecraft logs if anything goes wrong
				return;
			}
		}).start();
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

	private static boolean binarySearch(String languageCode) {	//Used to handle user error
		String LanguageCodes[] = {"af", "am", "ar", "az", "be", "bg", "bn", "bs", "ca", "co",
								  "cs", "cy", "da", "de", "el", "en", "eo", "es", "et", "eu", 
								  "fa", "fi", "fr", "fy", "ga", "gd", "gl", "gu", "ha", "hi", 
								  "hr", "ht", "hu", "hy", "id", "ig", "is", "it", "iw", "ja", 
								  "jw", "ka", "kk", "km", "kn", "ko", "ku", "ky", "la", "lb", 
								  "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mr", "ms", 
								  "mt", "my", "ne", "nl", "no", "ny", "pa", "pl", "ps", "pt", 
								  "ro", "ru", "sd", "si", "sk", "sl", "sm", "sn", "so", "sq", 
								  "sr", "st", "su", "sv", "sw", "ta", "te", "tg", "th", "tl", 
								  "tr", "uk", "ur", "uz", "vi", "xh", "yi", "yo", "zu"};

		int mid = 0;
		int low = 0;
		int high = LanguageCodes.length-1;
		
		 while (high >= low) {
			 mid = (high + low) / 2;
		     if (LanguageCodes[mid].compareTo(languageCode) < 0) {
		   	  	low = mid + 1;
		     } else if (LanguageCodes[mid].compareTo(languageCode) > 0) {
		    	 high = mid - 1;
		     } else {
		    	 return true; 
		     }
		}
		 
		return false;
	}
	
	public static String getMessage() {	//Static so copy command can access
		return translatedMessage;
	}
	
}
