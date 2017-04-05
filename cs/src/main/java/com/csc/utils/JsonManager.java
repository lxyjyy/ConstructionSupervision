package com.csc.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonManager {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String writeEntity2Json(Object o) throws JsonGenerationException, JsonMappingException, IOException {

        return objectMapper.writeValueAsString(o);

    }

    public static String writeList2Json(List<?> list) throws JsonGenerationException, JsonMappingException, IOException {

        return objectMapper.writeValueAsString(list);
    }

    public static String writeMap2Json(Map<String, String> map) throws JsonGenerationException, JsonMappingException, IOException {

        return objectMapper.writeValueAsString(map);

    }

    public static String writeMap2Json(Object map) throws JsonGenerationException, JsonMappingException, IOException {

        return objectMapper.writeValueAsString(map);

    }

    public static Object readJson2Entity(String json, Class<?> cls) throws JsonParseException, JsonMappingException, IOException {

        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

        Object o = objectMapper.readValue(json, cls);

        return o;
    }

    public static List<?> readJson2List(String json) throws JsonParseException, JsonMappingException, IOException {

        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

        List<?> list = objectMapper.readValue(json, List.class);
        return list;

    }

    public static Object[] readJson2Array(String json) throws JsonParseException, JsonMappingException, IOException {

        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

        Object[] arr = objectMapper.readValue(json, Object[].class);
        return arr;

    }

    public static Map<String, Object> readJson2Map(String json) throws JsonParseException, JsonMappingException, IOException {

        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

        @SuppressWarnings("unchecked")
        Map<String, Object> maps = objectMapper.readValue(json, Map.class);
        return maps;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object getTData(Map m, Class<?> cls) {

        Object data = null;
        String temp = null;

        try {
            temp = JsonManager.writeMap2Json(m);
            data = JsonManager.readJson2Entity(temp, cls);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    public static List<?> getTData(String json, Class<?> cls) {

        List<?> data = null;
        List<?> temp = null;

        try {
            data = JsonManager.readJson2List(json.toLowerCase());
            temp = getTData(data, cls);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return temp;
    }

    public static List<?> getTData(List<?> data, Class<?> cls) throws JsonGenerationException, JsonMappingException, IOException {

        List<Object> temp = null;

        if (data != null && data.size() > 0) {

            temp = new ArrayList<Object>();

            for (Object o : data) {

                @SuppressWarnings("unchecked")
                String s = writeMap2Json((Map<String, String>) o);

                temp.add(readJson2Entity(s, cls));

            }

        }

        return temp;
    }

}
