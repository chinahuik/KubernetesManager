#!/bin/bash
set -ex
git pull
mvn clean package -D maven.test.skip=true

#java -jar target/k8s-manager.jar
