package com.sir.todorestservicewithdynamodb.model;

import com.sir.todorestservicewithdynamodb.annotation.DynamoDbEntityDeclaration;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
@DynamoDbEntityDeclaration(tableName = "user_entity")
public class UserEntity {
    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("email")})
    private String email;

    @Getter(onMethod_ = {@DynamoDbAttribute("name")})
    private String name;

    @Getter(onMethod_ = {@DynamoDbAttribute("password")})
    private String password;

    @Getter(onMethod_ = {@DynamoDbAttribute("roles")})
    private List<String> roles;

    @Getter(onMethod_ = {@DynamoDbAttribute("refresh_token")})
    private String refreshToken;
}
