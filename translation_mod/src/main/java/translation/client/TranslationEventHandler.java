package translation.client;


import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.io.IOException;

import translation.translate.Translator;


public class TranslationEventHandler {
	
	private Translator http;
	
	public TranslationEventHandler() throws IOException{
		this.http = new Translator();
	}
	
	@SubscribeEvent
	public void translation(ClientChatReceivedEvent chatMessage) throws IOException {
		
		int indexOfColon = chatMessage.getMessage().getUnformattedText().indexOf(":");
		
		if(indexOfColon == -1) {
			return;
		}
		
		ITextComponent originalComponent = chatMessage.getMessage();
		
		new Thread(() -> {
			try {
				String outputMsg = http.translate(originalComponent.getUnformattedText().substring(indexOfColon+1));
				
				if(outputMsg.equals("")) {
					return;
				}
				
				outputMessage(outputMsg);
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}
		}).start();
			
//		if(translatedMsg.equals("")) {
//			return;
//		}
//		
//		ITextComponent translatedMessage = new TextComponentString(translatedMsg);
//		
//		Style hoverEventTranslation = new Style(); 
//		hoverEventTranslation.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatedMessage));
//		
//		ITextComponent translatedAddition = new TextComponentString(" [T]");
//		translatedAddition.setStyle(hoverEventTranslation);
//		
//		originalComponent.appendSibling(translatedAddition);
//		
//		chatMessage.setMessage(originalComponent);
		
	}

	public void outputMessage(String incomingText) {
		
		String outputText = "[Translation Mod]" + incomingText;
		
		ITextComponent outputComponent = new TextComponentString(outputText);
		 
		Minecraft.getMinecraft().player.sendMessage(outputComponent);
		
	}
	
}
