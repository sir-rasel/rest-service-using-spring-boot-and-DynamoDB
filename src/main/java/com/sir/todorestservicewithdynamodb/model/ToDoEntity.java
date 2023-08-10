package com.sir.todorestservicewithdynamodb.model;

import com.sir.todorestservicewithdynamodb.annotation.DynamoDbEntityDeclaration;
import com.sir.todorestservicewithdynamodb.constant.TodoTableIndexNames;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
@DynamoDbEntityDeclaration(tableName = "todo_entity")
public class ToDoEntity {
    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("user_email"),
            @DynamoDbSecondarySortKey(indexNames = TodoTableIndexNames.TODO_STATUS_INDEX)})
    private String userEmail;

    @Getter(onMethod_ = {@DynamoDbAttribute("todo_id"),
            @DynamoDbSecondaryPartitionKey(indexNames = TodoTableIndexNames.ID_INDEX)
    })
    private String id;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("created_at")})
    private String createdAt;

    @Getter(onMethod_ = {@DynamoDbAttribute("finished_before")})
    private String finishedBefore;

    @Getter(onMethod_ = {@DynamoDbAttribute("task_description")})
    private String taskDescription;

    @Getter(onMethod_ = {@DynamoDbAttribute("status"),
            @DynamoDbSecondaryPartitionKey(indexNames = TodoTableIndexNames.TODO_STATUS_INDEX)
    })
    private String status;

    @Getter(onMethod_ = {@DynamoDbAttribute("started_at")})
    private String startedAt;

    @Getter(onMethod_ = {@DynamoDbAttribute("completed_at")})
    private String completedAt;
}
