package com.ebuild.commerce.config;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class JsonHelper {

  private final ObjectMapper mapper;

  public JsonHelper(){
    //mapper 초기화
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  }

  /**
   *
   * @param obj Json 문자열로 직렬화할 객체
   * @return 직렬화된 Json 문자열
   */
  public String serialize(Object obj) {
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
  public <T> T deserialize(String jsonString, Class<T> type) {
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
  public String readFileAsJsonString(String path){
    try {
      return new String(Files.readAllBytes(Paths.get(path)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EMPTY;
  }

}
