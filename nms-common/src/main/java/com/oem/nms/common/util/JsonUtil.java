package com.oem.nms.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * @author Seaway John
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ignored) {
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException ignored) {
        }

        return null;
    }

}
