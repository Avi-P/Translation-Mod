package translation.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import translation.client.TranslationEventHandler;

/*
 * This command is used to copy whatever text
 * the user just had translated using the
 * ./translate command.
 */
public class CopyCommand implements ICommand {
	
	@Override
	public int compareTo(ICommand arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {	//Name of command
		// TODO Auto-generated method stub
		return "copy";
	}

	@Override
	public String getUsage(ICommandSender sender) {	//Usage of the command
		// TODO Auto-generated method stub
		return "/copy";
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		List<String> aliases = new ArrayList<String>();
		aliases.add("/copy");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if(TranslateCommand.getMessage().equals("")) {	//If nothing is there to copy, don't do anything
			return;
		}
		
		StringSelection selection = new StringSelection(TranslateCommand.getMessage().substring(12));
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);	//Copies the translated text
		
		TranslationEventHandler.outputMessage("Message Copied");
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