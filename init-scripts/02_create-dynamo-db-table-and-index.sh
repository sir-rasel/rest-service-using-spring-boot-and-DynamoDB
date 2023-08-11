#!/bin/bash

#user table
echo "########### Creating table with local secondary index ###########"
aws   $AWS_ENDPOINT \
      dynamodb create-table \
         --table-name user_entity \
         --attribute-definitions \
           AttributeName=email,AttributeType=S \
        --key-schema AttributeName=email,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3

echo "########### Describing a table ###########"
aws   $AWS_ENDPOINT \
      dynamodb describe-table --table-name user_entity --output table


#todo table
echo "########### Creating table with local secondary index ###########"
aws   $AWS_ENDPOINT \
      dynamodb create-table \
         --table-name todo_entity \
         --attribute-definitions \
           AttributeName=user_email,AttributeType=S \
           AttributeName=created_at,AttributeType=S \
        --key-schema AttributeName=user_email,KeyType=HASH \
          AttributeName=created_at,KeyType=RANGE \
        --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3

echo "########### Describing a table ###########"
aws   $AWS_ENDPOINT \
      dynamodb describe-table --table-name todo_entity --output table