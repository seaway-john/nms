#!/bin/bash

set -e

version=$(awk -F ' ' '$1=="version" {print $2}' ../../../../build.gradle | sed "s/'//g")
project=$(awk -F : '$1=="    name" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')
http_port=$(awk -F : '$1=="  http-port" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')
https_port=$(awk -F : '$1=="  https-port" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')

docker run -p ${http_port}:${http_port} -p ${https_port}:${https_port} --name=oem-${project} --restart=always -d oem/${project}:${version}
