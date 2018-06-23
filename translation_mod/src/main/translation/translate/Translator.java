package main.translation.translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;

public class Translator {

	public static void main(String[] args) throws Exception {

		  Translator http = new Translator();
		  
		  //Informal Tests
		  String word = http.callUrlAndParseResult("en", "es", "!@#$%^&*()");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", "_-+=`~{}[]|:;'<,>.?/\\");
		  System.out.println(word);  
		  
		  word = http.callUrlAndParseResult("en", "es", "Hi! Hi? Hi.");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", "Hi! Hi");
		  System.out.println(word);

		  word = http.callUrlAndParseResult("en", "es", "!Hi ?Hi .Hi");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", "Hello");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", "1234567890");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", "?_?");
		  System.out.println(word);
		  
		  word = http.callUrlAndParseResult("en", "es", ">.>");
		  System.out.println(word);
		  
		  
	}
 
	public String callUrlAndParseResult(String langFrom, String langTo, String text) throws Exception {

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
		 
		  return parseResult(periodCount(text), response.toString());
	}
 
	private String parseResult(int count, String inputJson) throws Exception {
  
		  JSONArray jsonArray1 = new JSONArray(inputJson);
		  JSONArray jsonArray2 = (JSONArray) jsonArray1.get(0);
		  
		  
		  if(count == 0) {
			  
			  JSONArray jsonArray = (JSONArray) jsonArray2.get(0);
			  return jsonArray.get(0).toString();
			  
		  }
		  
		  StringBuffer translatedText = new StringBuffer();
		  
		  for(int i = 0; i < count; i++) {
			  
			  try {
				  
				  JSONArray jsonArray = (JSONArray) jsonArray2.get(i);
				  translatedText.append(jsonArray.get(0).toString());
				  
			  } catch(org.json.JSONException e) {
				  
				  
			  }
			  
		  }
		  
		  return translatedText.toString();
	}
	
	private int periodCount(String text) {
		
		int count = 0;
		
		for(int i = 0; i < text.length(); i++) {
			
			if(text.charAt(i) == 46 || text.charAt(i) == 33 || text.charAt(i) == 63) {
				
				count++;
				
			}
			
		}
		
		if(text.charAt(text.length()-1) != 46 && text.charAt(text.length()-1) != 33 && text.charAt(text.length()-1) != 63) {
			
			count++;
			
		}
		
		return count;
	}
	
}