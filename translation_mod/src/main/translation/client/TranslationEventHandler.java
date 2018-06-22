package main.translation.client;


import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import java.util.List;
import java.util.ArrayList;
import main.translation.translate.Translator;



public class TranslationEventHandler {
	
	Minecraft mc;
	GuiScreen screen;
	
	@SubscribeEvent
	public void translation(ClientChatReceivedEvent chatMessage) {
		
		//component.getStyle().getHoverEvent();
		ITextComponent message = chatMessage.getMessage();
		
		Style stylish = new Style(); 
		
		Translator http = new Translator();
		
		ITextComponent translatedMessage;
		
		try {
			translatedMessage = new TextComponentString(http.callUrlAndParseResult("es", "en", message.getUnformattedComponentText()));
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
		
		//itextcomponent instanceof TextComponentString;
		
		//if(hover.getValue() == this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY())) {}
//		System.out.println("BEFORE ITEXTCOMPONENT");
//		
//		ITextComponent var;
//		
//		try {
//			var = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
//		} catch (NullPointerException e) {
//			return;
//		}
//		
//		System.out.println("AFTER ITEXT");
//		
//		if(var instanceof TextComponentString) {
//			String chatLine = var.getUnformattedComponentText();
//			
//			String word = null;
//			
//			try {
//				System.out.println("Here");
//				if(chatLine == null) {
//					return;
//				}
//				word = http.callUrlAndParseResult("es", "en", chatLine);
//			} catch (Exception e) {
//				word = null;
//			}
//			
//			if(word != null) {
//				List<String> translateList = new ArrayList<String>();
//				translateList.add(word);
//				
//				screen.drawHoveringText(translateList, Mouse.getX(), Mouse.getY());
//				
//				
//				
//			}
//			
//			
//		}
	}
}
