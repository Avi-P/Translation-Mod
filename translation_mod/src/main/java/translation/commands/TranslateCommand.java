package translation.commands;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import translation.client.TranslationEventHandler;
import translation.settings.SettingParser;

public class TranslateCommand implements ICommand {
	
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
		if(SettingParser.toggle == false) {	//Blocks translations from happening
			TranslationEventHandler.outputMessage("Translations are currently disabled. Enable in Mod Settings");
			return;
		}
		
		
		new Thread(() -> {	//New thread to overcome the blocking function when querying the server
			try {

				String inputLanguage = args[0];
				String outputLanguage = args[1];
				StringBuilder text = new StringBuilder();	//Used to build the string that will be translated
				
				for(int i = 2; i <= args.length-1; i++) {
					text.append(args[i]+" ");
				}
				
				String toBeTranslated = text.toString();	//Final text to be translated
			
				String outputMsg = TranslationEventHandler.getHttp().customTranslate(toBeTranslated, inputLanguage, outputLanguage);
				
				if(outputMsg.equals("")) {	//This case indicates that nothing should be outputted to client
					return;
				}
				
				TranslationEventHandler.outputMessage("[Entered Text] " + toBeTranslated);	//Outputs original text
				TranslationEventHandler.outputMessage(outputMsg);	//Outputs the translated text
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

}
