#!/bin/bash

set -e

version=$(awk -F ' ' '$1=="version" {print $2}' ../build.gradle | sed "s/'//g")

function exist_image() {
  exist_image=$(docker images | awk -v project="oem/${1}" -v version=${2} -F " " '$1==project && $2==version {printf "%s:%s",$1,$2}')

  if [[ -n "${exist_image}" ]]; then
    return 0
  fi

  return 1
}

echo "Start to exec build.sh"
./build.sh ${version}

images=''
bases=(nms-java8 nms-mongo nms-mysql nms-rabbitmq nms-redis)
for base in ${bases[@]}; do
  if ! exist_image ${base} 'latest'; then
    echo "Error: Image oem/${base}:latest not exist"
    exit 1
  fi

  images="${images} oem/${base}:latest"
done

projects=(nms-admin nms-discovery nms-gateway nms-log nms-manager nms-ui nms-websocket)
for project in ${projects[@]}; do
  if ! exist_image ${project} ${version}; then
    echo "Error: Image oem/${project}:${version} not exist"
    exit 1
  fi

  images="${images} oem/${project}:${version}"
done

echo "Start to export docker images ${images}"
docker save -o ./image.tar ${images}

if [[ ! -e ./image.tar ]]; then
  echo "Error: image.tar not exist"
  exit 1
fi

name=oem-nms_release.v${version}

rm -rf ./${name}
rm -rf ./${name}.tar.gz
mkdir ./${name}
cd ./${name}

mv ../image.tar ./

scp -r ../nms-compose.yml ./
for project in ${projects[@]}; do
  sed -i "s/oem\/${project}:latest/oem\/${project}:${version}/g" ./nms-compose.yml
done

scp -r ../install.sh ./
scp -r ../start.sh ./
scp -r ../stop.sh ./
chmod +x ./*.sh

cd ..

echo "Start to package ${name}.tar.gz"
tar -zcvf ./${name}.tar.gz ./${name}

rm -rf ./${name}

echo "Success to package ${name}.tar.gz"
