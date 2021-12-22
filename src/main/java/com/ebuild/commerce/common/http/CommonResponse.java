package com.ebuild.commerce.common.http;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@Builder
public class CommonResponse {

    private Map<String, Object> data;
    private Error error;

    public static CommonResponse OK(){
        Map<String, Object> data = Maps.newHashMap();
        data.put("result","OK");
        return CommonResponse.of(data, null);
    }

    /**
     * @param data data to insert directly
     * @return CommonResponse
     */
    public static CommonResponse OK(Map<String, Object> data){
        return CommonResponse.of(data, null);
    }

    /**
     * @param pair left: key, right: value
     * @return CommonResponse
     */
    public static CommonResponse OK(Pair<String, Object> pair){
        Map<String, Object> data = Maps.newHashMap();
        put(data, pair);
        return CommonResponse.of(data, null);
    }

    /**
     * @param pairs pair list :: left: key, right: value
     * @return CommonResponse
     */
    public static CommonResponse OK(List<Pair<String, Object>> pairs){
        Map<String, Object> data = Maps.newHashMap();
        for (Pair<String, Object> pair : pairs)
            put(data, pair);
        return CommonResponse.of(data, null);
    }

    public static CommonResponse ERROR(Exception e) {
        if ( e instanceof MethodArgumentNotValidException )
            return CommonResponse.of(null, Error.of(((MethodArgumentNotValidException)e).getFieldErrors()));
        return CommonResponse.of(null, Error.of(e));
    }

    private static CommonResponse of(Map<String, Object> data, Error error){
        return CommonResponse.builder()
                .data(data)
                .error(error)
                .build();
    }

    private static void put(Map<String, Object> data, Pair<String, Object> pair) {
        data.put(pair.getLeft(), pair.getRight());
    }

}