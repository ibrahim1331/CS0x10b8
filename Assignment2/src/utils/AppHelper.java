package utils;

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
}
