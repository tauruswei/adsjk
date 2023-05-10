DOCKER_TAG=citicbank.com/platform-service:1.0
OS=${shell arch | sed s/arm64/aarch_64/ | sed s/aarch64/aarch_64/ | sed s/amd64/x86_64/ }
DOCKER_FILE=Dockerfile.local

DOCKER_FILE_DB=script/docker/Dockerfile.db
DOCKER_TAG_DB=cosd/db:latest

# sentinel dashboard 相关
DOCKER_FILE_SENTINEL=script/docker/Dockerfile.sentinel
DOCKER_TAG_SENTINEL=cosd/sentinel-dashboard:latest


ifeq ($(OS),aarch_64)
	DOCKER_FILE_DB=script/docker/Dockerfile.db.M1
endif

default:build

build:sentinel
	docker build -f ${DOCKER_FILE} -t ${DOCKER_TAG} .

.PHONY: build

build-1:
	docker build -t ${DOCKER_TAG} .
.PHONY: build-1

sentinel:
	docker build -f ${DOCKER_FILE_SENTINEL} -t ${DOCKER_TAG_SENTINEL} .
.PHONY: sentinel

db:
	docker build -f ${DOCKER_FILE_DB} -t ${DOCKER_TAG_DB} .
.PHONY: sentinel


