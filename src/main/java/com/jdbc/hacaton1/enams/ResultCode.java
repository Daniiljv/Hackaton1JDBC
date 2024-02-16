package com.jdbc.hacaton1.enams;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ResultCode {

    SUCCESS(200),
    FAIL(400),
    NOT_FOUND(404);

    final int httpCode;
}