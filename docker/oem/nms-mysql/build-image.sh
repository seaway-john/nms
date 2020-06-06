#!/bin/bash

set -e

docker build -f Dockerfile -t oem/nms-mysql:latest ./
