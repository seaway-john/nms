#!/bin/bash

set -e

function log_exec() {
  echo $(date) ${1} >>./log-exec.log
}

log_exec "Start to exec stop.sh to stop and clean NMS containers"

yml_images=$(cat ./nms-compose.yml | grep oem/nms- | awk -F'image: ' '{print $2}' | sort)
container_images=$(docker ps -a | grep oem/nms- | awk '{print $2}' | sort)

all_include="true"
for container_image in ${container_images}; do
  exist="false"
  for yml_image in ${yml_images}; do
    if [[ ${container_image} == ${yml_image} ]]; then
      exist="true"
      break
    fi
  done

  if [[ ${exist} == "false" ]]; then
    all_include="false"
    break
  fi
done

if [[ ${all_include} == "true" ]]; then
  if [[ -n "${container_images}" ]]; then
    echo "Start to stop and clean this NMS docker containers"
    docker-compose -f ./nms-compose.yml down
  fi
else
  exist_containers=$(docker ps -a -q --no-trunc --filter name=^/oem-nms-)
  if [[ -n "${exist_containers}" ]]; then
    echo "Start to stop and clean old NMS docker containers"
    docker stop ${exist_containers}
    docker rm ${exist_containers}
  fi
fi

echo "Success to clean all NMS docker containers"

log_exec "Success to exec stop.sh to stop and clean NMS containers"
