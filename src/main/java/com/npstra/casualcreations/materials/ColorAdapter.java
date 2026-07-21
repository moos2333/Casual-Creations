package com.npstra.casualcreations.materials;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else if (primitive.isString()) {
                String str = primitive.getAsString().trim();
                if (str.startsWith("#")) {
                    str = str.substring(1);
                } else if (str.startsWith("0x") || str.startsWith("0X")) {
                    str = str.substring(2);
                }
                try {
                    return Integer.parseInt(str, 16);
                } catch (NumberFormatException e) {
                    return 0xFFFFFF;
                }
            }
        }
        return 0xFFFFFF;
    }
}