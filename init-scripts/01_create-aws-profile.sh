#!/bin/bash
echo "########### Creating profile ###########"
aws configure set aws_access_key_id "DUMMYIDEXAMPLE" --profile=dynamodb-local
aws configure set aws_secret_access_key "DUMMYEXAMPLEKEY" --profile=dynamodb-local
aws configure set region ap-southeast-1 --profile=dynamodb-local

echo "########### Listing profile ###########"
aws configure list