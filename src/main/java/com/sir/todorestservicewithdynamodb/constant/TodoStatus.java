package com.sir.todorestservicewithdynamodb.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoStatus {
    IN_COMPLETE("in-complete"),
    COMPLETE("complete"),
    IN_PROGRESS("in-progress");

    private final String status;
}
