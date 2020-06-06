#!/bin/bash

set -e

if [[ "$#" -ne 1 ]]; then
  echo "Error: Illegal number of parameters"
  exit 1
fi

version=${1}
pwd=$(pwd)

function build() {
  cd ${pwd}/../${1}/src/main/docker/
  ./build-image.sh
}

function exist_image() {
  exist_image=$(docker images | awk -v project="oem/${1}" -v version=${2} -F " " '$1==project && $2==version {printf "%s:%s",$1,$2}')

  if [[ -n "${exist_image}" ]]; then
    return 0
  fi

  return 1
}

bases=(nms-java8 nms-mongo nms-mysql nms-rabbitmq nms-redis)
for base in ${bases[@]}; do
  if ! exist_image ${base} 'latest'; then
    cd ./oem/${base}
    ./build-image.sh
    cd ../..
  fi
done

projects=(nms-admin nms-discovery nms-gateway nms-log nms-manager nms-ui nms-websocket)
for project in ${projects[@]}; do
  if exist_image ${project} ${version}; then
    echo "Exist image oem/${project}:${version}, skip it"
    continue
  fi

  build ${project}

  exist_image=$(docker images | grep oem/${project} | grep ${version})
  if ! exist_image ${project} ${version}; then
    echo "Error: Failed to build image oem/${project}:${version}"
    exit 1
  fi
done
