package com.jdbc.hacaton1.enams;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ResultCodeAPI {

    SUCCESS("SUCCESS"),
    FAIL("FAIL"),
    NOT_FOUND("NOT_FOUND");


    String description;
}
