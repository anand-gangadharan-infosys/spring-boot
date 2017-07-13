#!/bin/bash

#cd ..
#gradle clean
#gradle build
#cd deploy
#cp ../build/libs/hello-service-0.1.0.jar .

#docker stop $(docker ps -a -q)
#docker rm $(docker ps -a -q)
#docker build -t="java-docker-container" .
#docker run -itd --rm java-docker-container

docker-compose down
docker-compose rm -f
docker-compose up --build -d
