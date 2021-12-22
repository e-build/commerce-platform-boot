package com.ebuild.commerce.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtils {

    private JsonUtils() {}

    private static final ObjectMapper mapper;

    private static class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(formatter.format(value));
        }
    }

    static {
        mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        mapper.registerModule(simpleModule);
    }

    public static ObjectMapper defaultMapper(){
        return mapper;
    }

    /**
     *
     * @param obj Json 문자열로 직렬화할 객체
     * @return 직렬화된 Json 문자열
     */
    public static String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

    /**
     *
     * @param jsonString 역직렬화할 문자열
     * @param type 바인딩할 객체 타입
     * @return 역직렬화된 객체
     */
    public static <T> T deserialize(String jsonString, Class<T> type) {
        try {
            return (T) mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path Json 파일 경로
     * @return 파일로 부터 읽은 Json 문자열
     */
    public static String readFileAsJsonString(String path){
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

}
