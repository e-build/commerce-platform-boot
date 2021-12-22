package com.ebuild.commerce.common.http;


import com.ebuild.commerce.util.RegexUtils;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.FieldError;

@Getter
@Setter
@ToString
public class Error {

    private String code;
    private String message;

    /**
     * validation fail exception
     * @param fieldErrors validation fail fields
     * @return Error
     */
    public static Error of(List<FieldError> fieldErrors){
        Error error = new Error();
            List<String> messageList = Lists.newArrayList();
        fieldErrors.forEach(e -> { messageList.add("["+e.getField()+"](은)는 "+e.getDefaultMessage()+" 입력된 값: ["+e.getRejectedValue()+"]"); });
        error.setMessage(String.join(", ", messageList));
        error.setCode("BAD_REQUEST_BODY");
        return error;
    }

    /**
     * common exception
     * @param e validation fail fields
     * @return Error
     */
    public static Error of(Exception e) {
        Error error = new Error();
        error.setCode(RegexUtils.camelCaseToUnderScoreUpperCase(e.getClass().getSimpleName()));
        error.setMessage(e.getMessage());
        return error;
    }

}
