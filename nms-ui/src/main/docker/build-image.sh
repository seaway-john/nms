#!/bin/bash

set -e

project=$(awk -F : '$1=="    name" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')

cd ../../../

folderName=$(echo ${PWD##*/})
if [[ ${folderName} != ${project} ]]; then
  echo "Error: This script should be running in docker folder"
  exit 1
fi

if [[ "$(ls -A ./src/main/resources/nms-web-ui/)" == "" ]]; then
  echo "Error: should build nms-web-ui and copy dist/* into resources/nms-web-ui/"
  exit 1
fi

version=$(awk -F ' ' '$1=="version" {print $2}' ../build.gradle | sed "s/'//g")

cd ./src/main/docker/

scp -r ../resources/nms.conf ./default.conf
scp -r ../resources/ssl/ ./
scp -r ../resources/nms-web-ui/ ./

timestamp=$(date '+%s')
cat >./about.json <<EOF
{
    "project": "${project}",
    "version": "${version}",
    "buildDate": "${timestamp}"
}
EOF

docker build -f Dockerfile -t oem/${project}:${version} ./

#rm -rf ../resources/nms-web-ui/*
rm -rf ./default.conf ./ssl/ ./nms-web-ui/ ./about.json
