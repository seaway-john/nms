#!/bin/bash

set -e

if [[ ! -e ./image.tar ]]; then
  echo "Error: image.tar not exist"
  exit 1
fi

function log_exec() {
  echo $(date) ${1} >>./log-exec.log
}

log_exec "Start to exec install.sh to re-install NMS"

echo "Start to clean NMS docker containers"
./stop.sh

yml_images=$(cat ./nms-compose.yml | grep oem/iaxc- | awk -F'image: ' '{print $2}' | sort)
docker_images=$(docker images | grep oem/iaxc- | awk '{print $1":"$2}' | sort)

old_docker_images=""
for docker_image in ${docker_images}; do
  exist="false"
  for yml_image in ${yml_images}; do
    if [[ ${docker_image} == ${yml_image} ]]; then
      exist="true"
      break
    fi
  done

  if [[ ${exist} == "false" ]]; then
    old_docker_images="${old_docker_images} ${docker_image}"
  fi
done

if [[ -n "${old_docker_images}" ]]; then
  isLoop="true"
  while [[ ${isLoop} == "true" ]]; do

    read -r -p "Do you want to clean all old NMS images? [Y/n] " input
    case ${input} in
    [yY][eE][sS] | [yY])
      isLoop="false"
      docker rmi ${old_docker_images}
      echo "Success to clean all old NMS images"
      ;;

    [nN][oO] | [nN])
      isLoop="false"
      echo "Cancel clean all old NMS images"
      ;;

    *)
      echo "Invalid input..."
      ;;
    esac
  done
fi

invalid_images=$(docker images -q -f "dangling=true" | tr "\n" " ")
if [[ -n "${invalid_images}" ]]; then
  echo "Start to clean invalid docker images"
  docker rmi ${invalid_images}
fi

echo "Start to load NMS docker images"
docker load <./image.tar

echo "Start to run NMS server"
./start.sh

log_exec "Success to exec install.sh to re-install NMS"
