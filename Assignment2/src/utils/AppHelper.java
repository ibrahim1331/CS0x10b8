package utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enums.Role;

public class AppHelper {
	private static Gson gson;
	
	public static Gson getGson(){
		if(gson==null){
			//register type adapter if default de/serialization is not good enough
			gson = new GsonBuilder()
				.registerTypeAdapter(Role.class, new RoleDeserializer())
				.registerTypeAdapter(Role.class, new RoleSerializer())
				.create();
		}
		return gson;
	}
	
	public static Timestamp getTimestamp(String datetime){
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		    Date parsedDate = dateFormat.parse(datetime);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    return timestamp;
		} catch(Exception ex){
			return null;
		}
	    
	}
	
	public static Timestamp getCurrentTimestamp(){
		Timestamp timestamp = new Timestamp(new Date().getTime());
		return timestamp;
	}
	
	public static String generatePin(){
		char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random random = new SecureRandom();
	    char[] result = new char[25];
	    for (int i = 0; i < result.length; i++) {
	        // picks a random index out of character set > random character
	        int randomCharIndex = random.nextInt(characterSet.length);
	        result[i] = characterSet[randomCharIndex];
	    }
	    return new String(result);
	}
}
