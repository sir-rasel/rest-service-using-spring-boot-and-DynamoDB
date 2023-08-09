package com.sir.todorestservicewithdynamodb.model;

import com.sir.todorestservicewithdynamodb.annotation.DynamoDbEntityDeclaration;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
@DynamoDbEntityDeclaration(tableName = "todo_entity")
public class ToDoEntity {
    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("user_email")})
    private String userEmail;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("todo_id")})
    private String id;

    @Getter(onMethod_ = {@DynamoDbAttribute("created_at"),
            @DynamoDbSecondaryPartitionKey(indexNames = "todo_created_at_index")
    })
    private String createdAt;

    @Getter(onMethod_ = {@DynamoDbAttribute("finished_before"),
            @DynamoDbSecondaryPartitionKey(indexNames = "todo_finished_before_index")
    })
    private String finishedBefore;

    @Getter(onMethod_ = {@DynamoDbAttribute("task_description")})
    private String taskDescription;

    @Getter(onMethod_ = {@DynamoDbAttribute("status")})
    private String status;

    @Getter(onMethod_ = {@DynamoDbAttribute("started_at")})
    private String startedAt;

    @Getter(onMethod_ = {@DynamoDbAttribute("completed_at")})
    private String completedAt;
}
