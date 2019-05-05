package com.farmershao.stock.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonUtil
 *
 * @author Shao Yu
 * @date 2019/5/5 9:47
 * @since   1.0.0
 **/
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 对象转json
     *
     * @param o 目标对象
     * @return json
     */
    public static String toJson(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (Exception e) {
            log.error("Json转换异常：", e);
        }
        return "";
    }

    /**
     * json 转对象
     *
     * @param json  json
     * @param clazz 目标对象类型
     * @param <T>   泛型
     * @return 目标对象
     */
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            if (!StringUtils.isEmpty(json)) {
                return OBJECT_MAPPER.readValue(json, clazz);
            }
        } catch (Exception e) {
            log.error("Json转换异常：", e);
        }
        return null;
    }

    /**
     * json 转List
     *
     * @param jsonArrayStr json
     * @param clazz        目标类.class
     * @param <T>          泛型
     * @return List<T>
     */
    public static <T> List<T> parseToArray(String jsonArrayStr, Class<T> clazz) {
        try {
            if (!StringUtils.isEmpty(jsonArrayStr)) {
                JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
                return (List<T>) OBJECT_MAPPER.readValue(jsonArrayStr, javaType);
            }
        } catch (Exception e) {
            log.error("Json转换异常：", e);
        }
        return new ArrayList<>();
    }

}
