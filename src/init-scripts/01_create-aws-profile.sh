#!/bin/bash
echo "########### Creating profile ###########"
aws configure set aws_access_key_id default_access_key --profile=dynamodb-local
aws configure set aws_secret_access_key default_secret_key --profile=dynamodb-local
aws configure set region ap-southeast-1 --profile=dynamdb-local

echo "########### Listing profile ###########"
aws configure list