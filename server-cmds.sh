#!/bin/bash

export IMAGE_NAME=$1
export DOCKER_USER=$2
export DOCKER_PASSWORD=$3

echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin
docker-compose up -d