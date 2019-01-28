#!/usr/bin/env bash
while true; sleep 1; do curl -i -X GET http://localhost:8080/actuator/prometheus | grep resilience4j_circuitbreaker_helloApi_failed ; done;