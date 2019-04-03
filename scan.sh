#!/usr/bin/env bash

set -eu

echo "Scanning now..."
# SONAR_CLOUD_TOKEN should be added in circleci project setting
sonar-scanner -X -Dsonar.login="$SONAR_CLOUD_TOKEN"
