package adapters;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonSerializer {
	private Gson serializer;

	public JsonSerializer(Gson serializer) {
		this.serializer = serializer;
	}

	public <T> T fromJson(String s, Type type) {
		return serializer.fromJson(s, type);
	}

	public String toJson(Object src) {
		return serializer.toJson(src);
	}
}
