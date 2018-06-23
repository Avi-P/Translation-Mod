package main.translation.client;


import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import main.translation.translate.Translator;


public class TranslationEventHandler {
	
	Minecraft mc;
	GuiScreen screen;
	
	@SubscribeEvent
	public void translation(ClientChatReceivedEvent chatMessage) {
		
		ITextComponent message = chatMessage.getMessage();
		
		Style stylish = new Style(); 
		
		Translator http = new Translator();
		
		ITextComponent translatedMessage;
		
		try {
			translatedMessage = new TextComponentString(http.callUrlAndParseResult("en", "es", message.getUnformattedComponentText()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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
