#!/usr/bin/env bash

host=10.0.0.183
port=8769
echo 'waiting for config-service.'
until $(curl --output /dev/null --silent --head --fail http://$host:$port/ping); do
    printf "."
    sleep 3
done

/usr/bin/java -jar -Dspring.profiles.active=prod /opt/lib/discovery-service-1.0-SNAPSHOT.jar