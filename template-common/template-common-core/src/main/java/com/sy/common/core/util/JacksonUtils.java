package com.sy.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sy.common.core.exception.UtilException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author Monster
 * @version v1.0
 */
public class JacksonUtils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = SpringUtils.getContext().getBean(ObjectMapper.class);
    }

    /**
     * Object to json string.
     *
     * @param obj obj
     * @return json string
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Object to json string byte array.
     *
     * @param obj obj
     * @return json string byte array
     * @throws UtilException if transfer failed
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  class of object
     * @param <T>  General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Class<T> cls) {
        try {
            return MAPPER.readValue(json, cls);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  {@link Type} of object
     * @param <T>  General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(byte[] json, Type cls) {
        try {
            return MAPPER.readValue(json, MAPPER.constructType(cls));
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param cls         class of object
     * @param <T>         General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Class<T> cls) {
        try {
            return MAPPER.readValue(inputStream, cls);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string byte array
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param cls  class of object
     * @param <T>  General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(String json, Class<T> cls) {
        try {
            return MAPPER.readValue(json, cls);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json json string
     * @param type {@link Type} of object
     * @param <T>  General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(String json, Type type) {
        try {
            return MAPPER.readValue(json, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param json          json string
     * @param typeReference {@link TypeReference} of object
     * @param <T>           General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Object.
     *
     * @param inputStream json string input stream
     * @param type        {@link Type} of object
     * @param <T>         General type
     * @return object
     * @throws UtilException if deserialize failed
     */
    public static <T> T toObj(InputStream inputStream, Type type) {
        try {
            return MAPPER.readValue(inputStream, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Json string deserialize to Jackson {@link JsonNode}.
     *
     * @param json json string
     * @return {@link JsonNode}
     * @throws UtilException if deserialize failed
     */
    public static JsonNode toObj(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    /**
     * Register sub type for child class.
     *
     * @param clz  child class
     * @param type type name of child class
     */
    public static void registerSubtype(Class<?> clz, String type) {
        MAPPER.registerSubtypes(new NamedType(clz, type));
    }

    /**
     * Create a new empty Jackson {@link ObjectNode}.
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(MAPPER.getNodeFactory());
    }

    /**
     * Create a new empty Jackson {@link ArrayNode}.
     *
     * @return {@link ArrayNode}
     */
    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(MAPPER.getNodeFactory());
    }

    /**
     * Parse object to Jackson {@link JsonNode}.
     *
     * @param obj object
     * @return {@link JsonNode}
     */
    public static JsonNode transferToJsonNode(Object obj) {
        return MAPPER.valueToTree(obj);
    }

    /**
     * construct java type -> Jackson Java Type.
     *
     * @param type java type
     * @return JavaType {@link JavaType}
     */
    public static JavaType constructJavaType(Type type) {
        return MAPPER.constructType(type);
    }
}
