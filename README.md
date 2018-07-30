# Translation Mod

## Purpose

This mod was made to break the language barrier between players of different countries. Keep in mind that translations are not perfect and can lead to misunderstandings. Current version is made for 1.12.2


## Features

- Real Time Chat Translation

- Commands
  
	- /translate \<Input Language> \<Output Language> \<Text>
    
	  - This command can be inputted into the chat and outputted will be a translation
    
	  - Keep in mind that \<Input Language> and \<Output Language> uses ISO 639-1 language codes.
  
  - /copy
    
	  - This command copies the latest translation from /translate into your clipboard.
    
    - Control + V to paste the translation once copied
    
  - /languagelist \<1-19>
  
    - List of all the languages and their language codes supported by the /translate command.
    
- Settings
  - Hover over the settings name to see what is supported. Don't worry, there is error handling code!
  
 ## Mod Usage
 Requires Forge
 
 Recommendations
 - Due to Translation API limitations, don't use Real Time Chat Translations all the time. If you do, the mod might return error text back to you in chat. Toggle real time chat translation settings to false if this does happen and turn it back on later (few hours). 
 - Keep the number of languages enabled for real time detection low to reduce chat spam.
 
 Due to translations not being perfect, use at your own risk.
 
 
 ## Credits
 Thanks to Google for Google Translation, Optimaize for the language detection library, Forge for the Modding API, and Mojang for Minecraft
