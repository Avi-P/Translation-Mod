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
	
	private List<LanguageProfile> languageProfiles = new LanguageProfileReader().read(SettingParser.Languages);
	private LanguageDetector languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
			.withProfiles(languageProfiles)
			.build();
	private TextObjectFactory textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
	private String translatedMessage = "";
	
	public Translator() throws IOException { }
	
	public String translate(String text) throws Exception {
		
		String inputText = parseInputString(text);
		
		if(inputText.equals("")) {
			return inputText;
		}
		
		TextObject textObject = textObjectFactory.forText(inputText);
		List<DetectedLanguage> lang = languageDetector.getProbabilities(textObject);
		
		if(lang.get(0).getLocale().getLanguage().equals(SettingParser.mainLanguage)) {
			return "";
		}
		
		
		try {
			callUrlAndParseResult(lang.get(0).getLocale().getLanguage(), SettingParser.mainLanguage, inputText);			
		} catch (Exception e) {
			return "Error. Could not translate.";
		}
	
		String finalMessage = "[" + lang.get(0).getLocale().getLanguage() + " -> " + SettingParser.mainLanguage + "]: " + translatedMessage;
		
		return finalMessage;
	
	}
	
	 public void callUrlAndParseResult(String langFrom, String langTo, String text) throws Exception {

		  String url = "https://translate.googleapis.com/translate_a/single?"+
		    "client=gtx&"+
		    "sl=" + langFrom + 
		    "&tl=" + langTo + 
		    "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");    
		  
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection(); 
		  con.setRequestProperty("User-Agent", "Mozilla/5.0");
		 
		  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();
		 
		  while ((inputLine = in.readLine()) != null) {
			  response.append(inputLine);
		  }
		  
		  in.close();
		 
		  setTranslatedMessage(parseResult(endPunctuationCount(text), response.toString()));
		 
	}
 
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
		  
		  for(int i = 0; i < count; i++) {
			  try {
				  JSONArray jsonArray = (JSONArray) jsonArray2.get(i);
				  translatedText.append(jsonArray.get(0).toString());
			  } catch(Exception e) {
				  return "Error. Could not translate.";  
			  }
		  }
		  
		  return translatedText.toString();
		  
	}
	
	private int endPunctuationCount(String text) {
		
		int count = 0;
		
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == 46 || text.charAt(i) == 33 || text.charAt(i) == 63) {	//"." "!" "?"
				count++;	
			}
		}
		
		if(text.charAt(text.length()-1) != 46 && text.charAt(text.length()-1) != 33 && text.charAt(text.length()-1) != 63) {
			count++;
		}
		
		return count;
		
	}
	
	private String parseInputString(String inputText) {
		int indexOfDelimiter = inputText.indexOf(SettingParser.delimiter);
		
		if(indexOfDelimiter == -1) {
			return "";
		}
		
		return inputText.substring(indexOfDelimiter+1);
	}

	public String getTranslatedMessage() {
		return translatedMessage;
	}

	public void setTranslatedMessage(String translatedMessage) {
		this.translatedMessage = translatedMessage;
	}
	
}