#!/bin/bash
# start the application
#docker-compose --env-file=./.env-M1 -f docker-compose-nacos-node-web.yaml down
docker-compose --env-file=./.env-M1  down
echo y| docker system prune
docker volume rm $(docker volume ls -qf dangling=true)
rm -rf data
