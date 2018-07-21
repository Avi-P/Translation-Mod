package translation.client;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

import translation.translate.Translator;
import translation.settings.SettingParser;

public class TranslationEventHandler {
	
	/*
	 * We use a single instance so we don't have to load
	 * up language files for language detection every time.
	 * Benefit: Faster/Better performance
	 * Trade Off: Have to restart client if user wants to
	 * 			  include more languages for detection
	 */
	private static Translator http;	//Static so it can be used by Translate Command
	
	public TranslationEventHandler() throws IOException{
		TranslationEventHandler.http = new Translator();
	}
	
	@SubscribeEvent
	public void translation(ClientChatReceivedEvent chatMessage) throws IOException {
		
		if(SettingParser.toggle == false) {	//Blocks real-time translation from happening
			return;
		}
		
		ITextComponent originalComponent = chatMessage.getMessage();
		
		/*
		 * New thread is used to overcome the blocking function
		 * when getting data back from server. This is done
		 * because Minecraft is single-thread based.
		 */
		new Thread(() -> {
			try {
				String outputMsg = http.autoTranslate(originalComponent.getUnformattedText());
				
				if(outputMsg.equals("")) {	//This case indicates that nothing should be outputted to client
					return;
				}
				
				outputMessage(outputMsg);
			} catch (Exception e1) {
				e1.printStackTrace();	//Prints to Minecraft logs if anything goes wrong
				return;
			}
		}).start();
			
		
	}

	/*
	 * Outputs the translated text to the client
	 */
	public static void outputMessage(String incomingText) {
		
		String outputText = "[Translation Mod] " + incomingText;
		
		ITextComponent outputComponent = new TextComponentString(outputText);
		 
		Minecraft.getMinecraft().player.sendMessage(outputComponent);	//Outputs to client
		
	}
	
	public static Translator getHttp() {	//Accessor method for http
		return http;
	}
	
}
