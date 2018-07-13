package translation.client;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

import translation.translate.Translator;

public class TranslationEventHandler {
	
	private Translator http;
	
	public TranslationEventHandler() throws IOException{
		this.http = new Translator();
	}
	
	@SubscribeEvent
	public void translation(ClientChatReceivedEvent chatMessage) throws IOException {
		
		ITextComponent originalComponent = chatMessage.getMessage();
		
		new Thread(() -> {
			try {
				String outputMsg = http.translate(originalComponent.getUnformattedText());
				
				if(outputMsg.equals("")) {
					return;
				}
				
				outputMessage(outputMsg);
				
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			
			}
		}).start();
			
		
	}

	public void outputMessage(String incomingText) {
		
		String outputText = "[Translation Mod]" + incomingText;
		
		ITextComponent outputComponent = new TextComponentString(outputText);
		 
		Minecraft.getMinecraft().player.sendMessage(outputComponent);
		
	}
	
}
