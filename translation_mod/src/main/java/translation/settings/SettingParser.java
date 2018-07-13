package translation.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class SettingParser {

	public static String mainLanguage;
	public static List<String> Languages = new ArrayList<String>();
	public static String delimiter;

	public static void configSetup() {
		mainLanguageParser();
		translateLanguageParser();
		delimiterParser();
	}
		
		
	private static void mainLanguageParser() {
		if(ClientSettings.mainLanguage.matches("[a-zA-Z]+")) {
			if(ClientSettings.mainLanguage.length() == 2) {
				mainLanguage = ClientSettings.mainLanguage;
			} else {	
				mainLanguage = "en";
			}
		} else {
			mainLanguage = "en";
		}
	}
		
	private static void translateLanguageParser() {
		
		if(ClientSettings.translateLanguages == "" || ClientSettings.translateLanguages == null 						|| ClientSettings.translateLanguages == " " || ClientSettings.translateLanguages.length() == 1) {
			
			if(mainLanguage == null || mainLanguage == "") {
				
				Languages = Arrays.asList("fr", "es", "en");
				
				return;
					
			} else {
					
				Languages =  Arrays.asList(mainLanguage);
					
				return;
					
			}
				
		}
			
		if(ClientSettings.translateLanguages.length() == 1) {
			Languages = Arrays.asList("fr", "es", "en");
			
			return;
		}
			
		try {
			
			StringTokenizer tokenizer = new StringTokenizer(ClientSettings.translateLanguages, " ");
			List<String> listOfLanguages = new ArrayList<String>();
			
			while (tokenizer.hasMoreTokens()) {
				String holderString = tokenizer.nextToken();
				
				if(holderString.length() == 2) {
					listOfLanguages.add(holderString);
				}
			}
				
			Languages = listOfLanguages;
			
			return;
			
		} catch (NullPointerException e) {
			
			if(mainLanguage == null || mainLanguage == "") {
				
				Languages = Arrays.asList("fr", "es", "en");
				return;
				
			} else {
				
				Languages = Arrays.asList(mainLanguage);
				return;
				
			}
			
		}

	}
		
	private static void delimiterParser() {
		
		if(ClientSettings.delimiter.length() == 1) {
			delimiter = ClientSettings.delimiter;
		} else {
			delimiter = ">";
		}
		
	}
	
}
