package utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import enums.Role;

public class RoleDeserializer implements JsonDeserializer<Role> {
	@Override
	public Role deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		return Role.fromInt(json.getAsInt());
	}
}
