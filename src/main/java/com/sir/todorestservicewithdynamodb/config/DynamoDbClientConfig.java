package com.sir.todorestservicewithdynamodb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.Projection;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
@Configuration
public class DynamoDbClientConfig {
    @Value("${aws.dynamoDb.endpoint}")
    private String endPoint;

    @Value("${aws.region}")
    private String region;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(endPoint))
                .region(Region.of(region))
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient())
                .build();
    }

    @Profile("dev")
    @Bean
    public ProvisionedThroughput defaultProvisionedThroughput() {
        return ProvisionedThroughput.builder()
                .readCapacityUnits(3L)
                .writeCapacityUnits(3L)
                .build();
    }

    @Profile("dev")
    @Bean
    public Projection defaultProjection() {
        return Projection.builder()
                .projectionType(ProjectionType.ALL)
                .build();
    }
}
