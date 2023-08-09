package com.sir.todorestservicewithdynamodb.constant;

public class TodoTableIndexNames {
    private TodoTableIndexNames() {
    }

    public static final String CREATED_AT = "todo_created_at_index";
    public static final String FINISHED_BEFORE = "todo_finished_before_index";
}
