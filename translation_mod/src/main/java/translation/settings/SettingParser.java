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
		if(binarySearch(ClientSettings.mainLanguage)) {
			
				mainLanguage = ClientSettings.mainLanguage;
				
		} else {	
			
				mainLanguage = "en";
				
		}
	}
		
	private static void translateLanguageParser() {
		
		if(ClientSettings.translateLanguages == "" || ClientSettings.translateLanguages == null 
				|| ClientSettings.translateLanguages == " " || ClientSettings.translateLanguages.length() == 1) {
			
			if(mainLanguage == null || mainLanguage == "") {
				
				Languages = Arrays.asList("fr", "es", "en");
				
				return;
					
			} else {
					
				Languages =  Arrays.asList(mainLanguage);
					
				return;
					
			}
				
		}
			
		try {
			
			StringTokenizer tokenizer = new StringTokenizer(ClientSettings.translateLanguages, " ");
			List<String> listOfLanguages = new ArrayList<String>();
			
			while (tokenizer.hasMoreTokens()) {
				String holderString = tokenizer.nextToken();
				
				if(binarySearch(holderString)) {
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
	
	private static boolean binarySearch(String languageCode) {
		String LanguageCodes[] = {"cs", "da", "de", "en", "es",
				"fi", "fr", "id", "it", "nl",
				"no", "pl", "pt", "ro", "sv", "tr", "vi"};

		int mid = 0;
		int low = 0;
		int high = LanguageCodes.length-1;
		
		 while (high >= low) {
			 mid = (high + low) / 2;
		     if (LanguageCodes[mid].compareTo(languageCode) < 0) {
		   	  	low = mid + 1;
		     } else if (LanguageCodes[mid].compareTo(languageCode) > 0) {
		    	 high = mid - 1;
		     } else {
		    	 return true; 
		     }
		}
		return false;
	}
	
	
}
