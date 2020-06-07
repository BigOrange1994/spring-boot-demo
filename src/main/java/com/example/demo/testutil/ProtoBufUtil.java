package com.example.demo.testutil;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.StringMapSchema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * protoBuf序列化工具类
 */
public class ProtoBufUtil {

    public ProtoBufUtil() {
    }
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
    private static Map<Class<?>, StringMapSchema<?>> mapSchema = new ConcurrentHashMap<Class<?>, StringMapSchema<?>>();


    private static Objenesis objenesis = new ObjenesisStd(true);

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     * 缓存StringMapSchema
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> StringMapSchema<T> getMapSchema(Class<T> clazz) {
        StringMapSchema<T> schema = (StringMapSchema<T>) mapSchema.get(clazz);
        if (schema == null) {
            schema = new StringMapSchema<T>(getSchema(clazz));
            if (schema != null) {
                mapSchema.put(clazz, schema);
            }
        }
        return schema;
    }

    @SuppressWarnings({ "unchecked" })
    public static <T> byte[] serializer(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            T message = (T) objenesis.newInstance(clazz);
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 序列化Map对象，V不应为Object
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> byte[] serializeMapNotObjectClassValue(Map<String, V> map) {
        if (map == null) {
            throw new IllegalArgumentException("不能序列化空对象!");
        }
        StringMapSchema<V> schema = getMapSchema((Class<V>) map.values().iterator().next().getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(map, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化map对象时发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    /**
     * 反序列化Map集合，clazz不应为Object
     *
     * @param dataByte
     * @param clazz
     * @return
     */
    public static <V> Map<String, V> deserializeMapNotObjectClassValue(byte[] dataByte, Class<V> clazz) {
        if (dataByte == null || dataByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        if (Object.class == clazz) {
            throw new IllegalArgumentException("不能反序列化成Map<String, Object>对象!");
        }
        Map<String, V> instance = new HashMap<String, V>();
        StringMapSchema<V> schema = getMapSchema(clazz);
        ProtostuffIOUtil.mergeFrom(dataByte, instance, schema);
        return instance;
    }

    /**
     * 序列化Map集合
     *
     * @param map
     * @param clazz
     * @return
     */
    public static <V> byte[] serializeMap(Map<String, V> map, Class<V> clazz) {
        if (map == null) {
            throw new IllegalArgumentException("不能序列化空对象!");
        }
        if (clazz == Object.class) {
            MapDto mapDto = new MapDto();
            mapDto.setMap(map);
            return serializer(mapDto);
        } else {
            return serializeMapNotObjectClassValue(map);
        }

    }

    /**
     * 反序列化Map集合
     *
     * @param dataByte
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V> Map<String, V> deserializeMap(byte[] dataByte, Class<V> clazz) {
        if (dataByte == null || dataByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        if (Object.class == clazz) {
            return (Map<String, V>) deserializer(dataByte, MapDto.class).getMap();
        } else {
            return deserializeMapNotObjectClassValue(dataByte, clazz);
        }

    }

    private static class MapDto {
        private Map<String, ? extends Object> map;

        public Map<String, ? extends Object> getMap() {
            return map;
        }

        public void setMap(Map<String, ? extends Object> map) {
            this.map = map;
        }
    }

}
