package com.npstra.casualcreations.materials;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    private static final int DEFAULT_COLOR = 0xFFFFFF;
    private static final int COLOR_MASK = 0xFFFFFF;

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) return JsonNull.INSTANCE;
        return new JsonPrimitive(String.format("#%06X", src & COLOR_MASK));
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !json.isJsonPrimitive()) return DEFAULT_COLOR;
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            return primitive.getAsInt() & COLOR_MASK;
        }
        if (primitive.isString()) {
            String str = primitive.getAsString().trim();
            if (str.startsWith("#")) {
                str = str.substring(1);
            } else if (str.startsWith("0x") || str.startsWith("0X")) {
                str = str.substring(2);
            }
            if (str.isEmpty()) return DEFAULT_COLOR;
            try {
                return Integer.parseInt(str, 16) & COLOR_MASK;
            } catch (NumberFormatException e) {
                return DEFAULT_COLOR;
            }
        }
        return DEFAULT_COLOR;
    }
}