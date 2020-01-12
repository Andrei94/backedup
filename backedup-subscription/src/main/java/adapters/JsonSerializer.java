package adapters;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonSerializer {
	private Gson serializer;

	public JsonSerializer(Gson serializer) {
		this.serializer = serializer;
	}

	public <T> T fromJson(String json, Type type) {
		return serializer.fromJson(json, type);
	}

	public String toJson(Object src) {
		return serializer.toJson(src);
	}
}
