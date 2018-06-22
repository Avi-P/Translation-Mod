package main.translation.client;


import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
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
	public void translation(MouseEvent hover) {
		
		//component.getStyle().getHoverEvent();
		
		Translator http = new Translator();
		
		
		//itextcomponent instanceof TextComponentString;
		
		System.out.println("MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT MOUSE EVENT ");
		//if(hover.getValue() == this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY())) {}
		
		ITextComponent var = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
				
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
