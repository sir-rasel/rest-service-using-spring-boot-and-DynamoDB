#!/bin/bash

#user table
echo "########### Creating table with global secondary index ###########"
aws   $AWS_ENDPOINT \
      dynamodb create-table \
         --table-name user_entity \
         --attribute-definitions \
           AttributeName=email,AttributeType=S \
           AttributeName=name,AttributeType=S \
           AttributeName=password,AttributeType=S \
           AttributeName=roles,AttributeType=L \
           AttributeName=refresh_token,AttributeType=S \
        --key-schema AttributeName=email,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3

echo "########### Describing a table ###########"
aws   $AWS_ENDPOINT \
      dynamodb describe-table --table-name user_entity --output table


#todo table
echo "########### Creating table with global secondary index ###########"
aws   $AWS_ENDPOINT \
      dynamodb create-table \
         --table-name todo_entity \
         --attribute-definitions \
           AttributeName=user_email,AttributeType=S \
           AttributeName=todo_id,AttributeType=S \
           AttributeName=created_at,AttributeType=S \
           AttributeName=finished_before,AttributeType=S \
           AttributeName=task_description,AttributeType=S \
           AttributeName=status,AttributeType=S \
           AttributeName=started_at,AttributeType=S \
           AttributeName=completed_at,AttributeType=S \
        --key-schema AttributeName=user_email,KeyType=HASH \
        --key-schema AttributeName=created_at,KeyType=RANGE \
        --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3 \
        --local-secondary-indexes \
                "[{\"IndexName\": \"todo_id_index\",
                \"KeySchema\":[{\"AttributeName\":\"todo_id\",\"KeyType\":\"HASH\"},
                \"Projection\":{\"ProjectionType\":\"INCLUDE\"}},

                {\"IndexName\": \"todo_status_index\",
                                \"KeySchema\":[{\"AttributeName\":\"status\",\"KeyType\":\"HASH\"},
                                              {\"AttributeName\":\"user_email\",\"KeyType\":\"RANGE\"}],
                                \"Projection\":{\"ProjectionType\":\"INCLUDE\"}}]"

echo "########### Describing a table ###########"
aws   $AWS_ENDPOINT \
      dynamodb describe-table --table-name todo_entity --output table