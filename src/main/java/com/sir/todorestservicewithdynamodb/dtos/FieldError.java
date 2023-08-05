package com.sir.todorestservicewithdynamodb.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {
    private String field;
    private String errorCode;
}
