#!/usr/bin/env bash
while true; sleep 1; do curl -X GET http://localhost:8080/rest -w "\n"; done;