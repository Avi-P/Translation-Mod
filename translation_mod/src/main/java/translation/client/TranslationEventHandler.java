package translation.client;


import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		
		System.out.println("Formatted Text: " + originalComponent.getFormattedText());
		System.out.println("Unformatted Text: " + originalComponent.getUnformattedText());
		System.out.println("Unformatted Component Text: " + originalComponent.getUnformattedComponentText());
		
		Style hoverEventTranslation = new Style(); 
		
		ITextComponent translatedMessage;
		
		try {
			translatedMessage = new TextComponentString(http.translate(originalComponent.getUnformattedText().substring(indexOfColon+1)));
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		hoverEventTranslation.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatedMessage));
		
		ITextComponent translatedAddition = new TextComponentString("");
		
		translatedAddition.appendText(" [T]");
		translatedAddition.setStyle(hoverEventTranslation);
		
		originalComponent.appendSibling(translatedAddition);
		
		
		
		chatMessage.setMessage(originalComponent);
		
	}

	
}
