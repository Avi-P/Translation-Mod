package main.translation.translate;

import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import java.util.List;
import java.util.ArrayList;


public class TranslationEventHandler {
	
	//component.getStyle().getHoverEvent();
	protected Minecraft mc;
	Translator http = new Translator();
	GuiScreen screen;
	
	@SubscribeEvent
	public void translation(HoverEvent hover) {
		
		//itextcomponent instanceof TextComponentString;
		
		
		//if(hover.getValue() == this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY())) {}
		
		ITextComponent var = hover.getValue();
				
		if(var instanceof TextComponentString) {
			String chatLine = var.getUnformattedComponentText();
			
			String word = null;
			
			try {
				word = http.callUrlAndParseResult("es", "en", chatLine);
			} catch (Exception e) {
				word = null;
			}
			
			if(word != null) {
				List<String> translateList = new ArrayList<String>();
				translateList.add(word);
				
				screen.drawHoveringText(translateList, Mouse.getX(), Mouse.getY());
				
				
				
			}
			
			
		}
	}
}
