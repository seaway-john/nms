#!/bin/bash

version=$(awk -F ' ' '$1=="version" {print $2}' ../../../../build.gradle | sed "s/'//g")
project=$(awk -F : '$1=="    name" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')
port=$(awk -F : '$1=="  port" {print $2}' ../resources/application.yml | sed -e 's/^[ \t]*//g' -e 's/[ \t]*$//g')

docker run -p ${port}:${port} --name=oem-${project} --restart=always -d oem/${project}:${version}
