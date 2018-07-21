package translation.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class SettingParser {

	public static String mainLanguage;
	public static List<String> Languages = new ArrayList<String>();
	public static String delimiter;
	public static boolean toggle;
	
	/*
	 * Calls functions that will properly handle problems in the config
	 */
	public static void configSetup() {
		mainLanguageParser();
		translateLanguageParser();
		delimiterParser();
		toggleParser();
	}
		
	/*
	 * Parses the main language user setting to ensure
	 * there are no problems. Handles any problems.
	 */
	private static void mainLanguageParser() {
		if(binarySearch(ClientSettings.mainLanguage)) {	//Makes sure user entered setting is supported
				mainLanguage = ClientSettings.mainLanguage;
		} else {	
				mainLanguage = "en";	//default language is English
		}
	}
		
	/*
	 * Parses the translate language user setting to ensure
	 * there are no problems. Handles any problems.
	 */
	private static void translateLanguageParser() {
		
		if(ClientSettings.translateLanguages == "" || ClientSettings.translateLanguages == null 
				|| ClientSettings.translateLanguages == " " || ClientSettings.translateLanguages.length() == 1) {	//Various cases that lead to errors
			
			if(mainLanguage == null || mainLanguage == "") {
				Languages = Arrays.asList("fr", "es", "en");	//Default values
				return;
			} else {
				Languages =  Arrays.asList(mainLanguage);	//Only mainLanguage user entered if proper
				return;
			}
				
		}
			
		try {
			
			StringTokenizer tokenizer = new StringTokenizer(ClientSettings.translateLanguages, " ");	//Splits the string
			List<String> listOfLanguages = new ArrayList<String>();
			
			while (tokenizer.hasMoreTokens()) {
				String holderString = tokenizer.nextToken();
				
				if(binarySearch(holderString)) {	//Makes sure the language is supported
					listOfLanguages.add(holderString);
				}
			}
				
			Languages = listOfLanguages;
			
			return;
			
		} catch (NullPointerException e) {
			
			if(mainLanguage == null || mainLanguage == "") {
				Languages = Arrays.asList("fr", "es", "en");	//Default values
				return;
			} else {
				Languages = Arrays.asList(mainLanguage);
				return;
			}
			
		}

	}
		
	/*
	 * Parses the delimiter user setting to ensure
	 * there are no problems. Handles any problems.
	 */
	private static void delimiterParser() {
		
		if(ClientSettings.delimiter.length() == 1) {	//Makes sure the user entered value is a length of one
			delimiter = ClientSettings.delimiter;
		} else {
			delimiter = ">";	//Default value
		}
		
	}
	
	/*
	 * Used to make sure whatever language code user enters
	 * is supported by language detection library.
	 */
	private static boolean binarySearch(String languageCode) {
		String LanguageCodes[] = {"cs", "da", "de", "en", "es",	//Supported codes
								  "fi", "fr", "id", "it", "nl",
							      "no", "pl", "pt", "ro", "sv", 
							      "tr", "vi"};

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
	
	private static void toggleParser() {
		toggle = ClientSettings.activated;
	}
}
