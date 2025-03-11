#!/bin/sh

sudo yum update -y && sudo yum install docker
sudo usermod -aG docker ec2-user
sudo systemctl start docker

# install docker compose

sudo curl -SL "https://github.com/docker/compose/releases/download/v2.20.3/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
