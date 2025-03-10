#!/bin/bash

IMAGE_NAME=$1
DOCKER_USER=$2
DOCKER_PASSWORD=$3

echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER--password-stdin
docker-compose up -d