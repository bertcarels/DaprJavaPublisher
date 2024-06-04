#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <new_version>"
    exit 1
fi


docker build --build-context src=../src -t javawhlpub:$1 . -f Dockerfile_arm  
