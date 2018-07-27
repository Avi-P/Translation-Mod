package translation.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;

import com.optimaize.langdetect.DetectedLanguage;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.TextObjectFactory;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;

import translation.settings.SettingParser;

public class Translator {

	/* 
	 * These objects are used to perform the Language detection
	 * and to store the translated message.
	 */
	private List<LanguageProfile> languageProfiles = new LanguageProfileReader().read(SettingParser.Languages);
	private LanguageDetector languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
			.withProfiles(languageProfiles)
			.build();
	private TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
	private String translatedMessage = "";
	
	//Constructor
	public Translator() throws IOException { }
	
	/* 
	 * Pass text in as input and a formatted translation will be 
	 * outputted in the user's main language. If any errors happen,
	 * an error message is returned.
	 */
	public String autoTranslate(String text) throws Exception {
		
		String inputText = parseInputString(text);
		
		if(inputText.equals("")) {	//Handles empty string being passed
			return inputText;
		}
		
		TextObject textObject = textObjectFactory.forText(inputText);
		List<DetectedLanguage> lang = languageDetector.getProbabilities(textObject);	//Stores the probabilities of languages found
		
		if(lang.get(0).getLocale().getLanguage().equals(SettingParser.mainLanguage)) {	//If the language is detected to be user's main language, return "" so that nothing is outputted
			return "";
		}
		
		try {	//Handles any error that happens when requesting to server
			callUrlAndParseResult(lang.get(0).getLocale().getLanguage(), SettingParser.mainLanguage, inputText);	//Uses highest probability language detected
		} catch (Exception e) {
			return "Error. Could not translate.";
		}
	
		String finalMessage = "[" + lang.get(0).getLocale().getLanguage() + " -> " + SettingParser.mainLanguage + "]: " + translatedMessage;	//Formatted message
		
		return finalMessage;
	
	}
	
	/*
	 * Used when user knows what is the input language. Doesn't parse
	 * string prior to translation.
	 */
	public String customTranslate(String inputLang, String outputLang, String text) throws Exception {	
		
		if(text.equals("")) {	//Handles empty string being passed
			return text;
		}
		
		try {	//Handles any error that happens when requesting to server
			callUrlAndParseResult(inputLang, outputLang, text);	//Uses highest probability language detected
		} catch (Exception e) {
			e.printStackTrace();
			return "Error. Could not translate.";
		}
	
		String finalMessage = "[" + inputLang + " -> " + outputLang + "]: " + translatedMessage;	//Formatted message
	
		return finalMessage;
	}
	
	 /* 
	  * Establishes connection with Google Translation server.
	  * Sends request with text to be translated and detected language of text.
	  * Gets back translation in JSON format.
	  * Calls function to construct the translated message and clean it up
	  */
	 public void callUrlAndParseResult(String langFrom, String langTo, String text) throws Exception {

		  String url = "https://translate.googleapis.com/translate_a/single?"+
		    "client=gtx&"+
		    "sl=" + langFrom + 
		    "&tl=" + langTo + 
		    "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");    
		  
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();	//Opens connection to server
		  con.setRequestProperty("User-Agent", "Mozilla/5.0");
		 
		  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));	//Gets ready to read incoming data. Blocking function.
		  String inputLine;
		  StringBuffer response = new StringBuffer();
		 
		  while ((inputLine = in.readLine()) != null) {	//Stores the data while data comes in
			  response.append(inputLine);
		  }
		  
		  in.close();
		 
		  setTranslatedMessage(parseResult(endPunctuationCount(text), response.toString()));
		 
	} 
 
	 /*
	  *  Due to how JSON returned is formatted, we must parse it to
	  * successfully construct the whole translated text. Multiple
	  * sentences are split into multiple JSON arrays so this function
	  * handles this.
	  */
	private String parseResult(int count, String inputJson) throws Exception {
  
		  JSONArray jsonArray1 = new JSONArray(inputJson);
		  JSONArray jsonArray2;
		  
		  try {
			  jsonArray2 = (JSONArray) jsonArray1.get(0);
		  } catch(ClassCastException e) {
			  return "Error. Could not translate.";
		  }
		  
		  if(count == 0) {
			  JSONArray jsonArray = (JSONArray) jsonArray2.get(0);
			  return jsonArray.get(0).toString();
		  }
		  
		  StringBuffer translatedText = new StringBuffer();
		  
		  for(int i = 0; i < count; i++) {	//Uses results from endPunctuationCount()
			  try {
				  JSONArray jsonArray = (JSONArray) jsonArray2.get(i);
				  translatedText.append(jsonArray.get(0).toString());	//Appends all data from the JSON array to make full translated message
			  } catch(Exception e) {
				  return "Error. Could not translate.";  
			  }
		  }
		  
		  return translatedText.toString();
		  
	}
	
	/* 
	 * JSON Array returned has JSON arrays in it which are
	 * split based on end punctuation. This function parses
	 * the input text and counts how many end punctuation
	 * there are and returns it. This information is used to 
	 * know how many times we will need to iterate through 
	 * the JSON Array returned from the server to construct 
	 * the full translation.
	 */
	private int endPunctuationCount(String text) {
		
		int count = 0;
		
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == 46 || text.charAt(i) == 33 || text.charAt(i) == 63) {	//"." "!" "?"
				count++;	
			}
		}
		
		if(text.charAt(text.length()-1) != 46 && text.charAt(text.length()-1) != 33 && text.charAt(text.length()-1) != 63) {	//Handles case where the end of a string contains an end punctuation
			count++;
		}
		
		return count;
		
	}
	
	/* 
	 * Used to detect whether the input text is a chat message
	 * is by a player. This function is also used to get rid of
	 * the user name part of the chat message and only keep
	 * the chat message part so that language detection
	 * and translation is better.
	 */
	private String parseInputString(String inputText) {
		int indexOfDelimiter = inputText.indexOf(SettingParser.delimiter);
		
		if(indexOfDelimiter == -1) {	//If delimiter not found, returns "" so that nothing is outputted
			return "";
		}
		
		return inputText.substring(indexOfDelimiter+1);	//Returns whatever is after delimiter
	}
	
	//Accessor method for translatedMessage
	public String getTranslatedMessage() {
		return translatedMessage;
	}

	//Mutator for translatedMessage
	public void setTranslatedMessage(String translatedMessage) {
		this.translatedMessage = translatedMessage;
	}
	
}