package com.sir.todorestservicewithdynamodb.model;

import com.sir.todorestservicewithdynamodb.annotation.DynamoDbEntityDeclaration;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
@DynamoDbEntityDeclaration(tableName = "todo_entity")
public class ToDoEntity {
    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("todo_id")})
    private String id;

    @Getter(onMethod_ = {@DynamoDbAttribute("created_at"),
            @DynamoDbSecondaryPartitionKey(indexNames = "todo_created_at_index")
    })
    private String createdAt;

    @Getter(onMethod_ = {@DynamoDbAttribute("description")})
    private String description;

    @Getter(onMethod_ = {@DynamoDbAttribute("status")})
    private String status;
}
