#!/bin/bash

set -e

function log_exec() {
  echo $(date) ${1} >>./log-exec.log
}

log_exec "Start to exec start.sh to restart NMS containers"

yml_images=$(cat ./nms-compose.yml | grep oem/nms- | awk -F'image: ' '{print $2}' | sort)
docker_images=$(docker images | grep oem/nms- | awk '{print $1":"$2}' | sort)

for yml_image in ${yml_images}; do
  exist="false"
  for docker_image in ${docker_images}; do
    if [[ ${docker_image} == ${yml_image} ]]; then
      exist="true"
      break
    fi
  done

  if [[ ${exist} == "false" ]]; then
    echo "Error: please install this NMS images firstly"
    exit 1
  fi
done

if [[ ! -f "/etc/localtime" ]]; then
  echo "Error: /etc/localtime not exist, you can try again after e.g. \"ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime\""
  exit 1
fi

if [[ -d "/etc/timezone/" ]]; then
  rm -rf /etc/timezone/
fi

if [[ ! -f "/etc/timezone" ]]; then
  timezone=$(ls -l /etc/localtime | awk -F'/usr/share/zoneinfo/' '{print $2}')
  if [[ -z "${timezone}" ]]; then
    echo "Error: /etc/timezone not exist, you can try again after e.g. \"echo 'Asia/Shanghai' > /etc/timezone\""
    exit 1
  fi

  echo "Created /etc/timezone as ${timezone}"
  echo "${timezone}" >/etc/timezone
fi

docker-compose -f ./nms-compose.yml up -d
#docker-compose scale oem-nms-mongo=5

echo "Success to start NMS server"

log_exec "Success to exec start.sh to restart NMS containers"
