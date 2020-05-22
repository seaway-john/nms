#!/bin/bash

project=$(awk -F : '$1=="    name" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')

cd ../../../

folderName=$(echo ${PWD##*/})
if [[ ${folderName} != ${project} ]]; then
  echo "Error: This script should be running in docker folder"
  exit 1
fi

version=$(awk -F ' ' '$1=="version" {print $2}' ../build.gradle | sed "s/'//g")
../gradlew build -x test

if [[ ! -e ./build/libs/${project}-${version}.jar ]]; then
  echo "Jar ${project}-${version}.jar not exist"
  exit 1
fi

cd ./src/main/docker/

scp -r ../../../build/libs/${project}-${version}.jar ./app.jar
scp -r ../resources/application.yml ./application.yml

timestamp=$(date '+%s')
cat >./about.json <<EOF
{
    "project": "${project}",
    "version": "${version}",
    "buildDate": "${timestamp}"
}
EOF

docker build -f Dockerfile -t oem/${project}:${version} ./

rm -rf ./app.jar ./application.yml ./about.json
