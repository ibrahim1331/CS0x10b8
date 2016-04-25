package utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TimestampDeserializer implements JsonDeserializer<Timestamp> {
	@Override
	public Timestamp deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		    Date parsedDate = dateFormat.parse(json.getAsString());
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (Exception ex){
			return null;
		}
	}
}
