#!/usr/bin/env bash
docker run --rm -p 9090:9090 \
    -v /Users/tadamasatoshi/IdeaProjects/my-first-resilience4j/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus:v2.6.1