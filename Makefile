DOCKER_TAG=cosd/application:1.0
OS=${shell arch | sed s/arm64/aarch_64/ | sed s/aarch64/aarch_64/ | sed s/amd64/x86_64/ }
DOCKER_FILE=script/docker/Dockerfile

DOCKER_FILE_DB=script/docker/Dockerfile.db
DOCKER_TAG_DB=cosd/db:latest


ifeq ($(OS),aarch_64)
	DOCKER_FILE_DB=script/docker/Dockerfile.db.M1
endif

default:build

build:
	docker build -f ${DOCKER_FILE} -t ${DOCKER_TAG} .
.PHONY: build

db:
	docker build -f ${DOCKER_FILE_DB} -t ${DOCKER_TAG_DB} .
.PHONY: db


