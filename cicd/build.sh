#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <new_version>"
    exit 1
fi

set
echo $GITVERSION_FULLSEMVER

buildah build --env GITVERSION_FULLSEMVER --build-context src=../src -t javawhlpub:$1 --platform linux/amd64 .
####docker tag javawhlpub:$1 bcacr2023.azurecr.io/kab-whl/javawhlpub:$1
####docker push bcacr2023.azurecr.io/kab-whl/javawhlpub:$1
