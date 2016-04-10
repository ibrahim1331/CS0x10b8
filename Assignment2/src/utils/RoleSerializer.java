package utils;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import enums.Role;

public class RoleSerializer implements JsonSerializer<Role> {

	@Override
	public JsonElement serialize(Role role, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(role.getValue());
	}

}
