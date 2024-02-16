package com.jdbc.hacaton1.models.responseMessageAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jdbc.hacaton1.enams.ResultCodeAPI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseMessageAPI<T> {
    T result;
    String answer;
    String details;
    String message;
    Integer code;

    public ResponseMessageAPI(T result, ResultCodeAPI resultCode, String details, String message, Integer code){
        this.result = result;
        this.answer = resultCode.getDescription();
        this.details = details;
        this.message = message;
        this.code = code;
    }
}
