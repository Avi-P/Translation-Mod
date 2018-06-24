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
		
		ITextComponent message = chatMessage.getMessage();
		
		Style stylish = new Style(); 
		
		ITextComponent translatedMessage;
		
		try {
			translatedMessage = new TextComponentString(http.translate(message.getUnformattedComponentText()));
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		stylish.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatedMessage));
		
		ITextComponent newMessage = new TextComponentString("");
		
		newMessage.appendText(message.getUnformattedText());
		newMessage.setStyle(stylish);
		
		chatMessage.setMessage(newMessage);
		
	}

	
}
